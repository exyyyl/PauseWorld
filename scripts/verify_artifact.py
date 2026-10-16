#!/usr/bin/env python3
"""Check release jars, including remapping data required outside development."""
import json
import glob
import re
import struct
import sys
from pathlib import Path
from zipfile import ZipFile


def verify(path):
    match = re.match(r"worldpause-(1\.20\.1|1\.21\.1)-(fabric|forge|neoforge)-", path.name)
    assert match, f"Unexpected artifact name: {path}"
    version, loader = match.groups()
    expected_major = 61 if version == "1.20.1" else 65
    with ZipFile(path) as jar:
        names = set(jar.namelist())
        config = json.loads(jar.read("projects.mixins.json"))
        assert config["required"] is True
        for mixin in config["mixins"] + config["client"]:
            classname = config["package"].replace(".", "/") + "/" + mixin + ".class"
            assert classname in names, f"Missing mixin: {classname}"
        for classname in (name for name in names if name.endswith(".class")):
            magic, _, major = struct.unpack(">IHH", jar.read(classname)[:8])
            assert magic == 0xCAFEBABE and major <= expected_major, f"Wrong Java version: {classname}"
        assert "projects.png" in names
        for language in ["en_us", "ru_ru", "de_de", "fr_fr", "es_es", "ja_jp", "pt_br", "zh_cn"]:
            strings = json.loads(jar.read(f"assets/projects/lang/{language}.json"))
            assert "key.projects.time_stop" in strings
        needs_refmap = loader == "fabric" or version == "1.20.1"
        if needs_refmap:
            refmap = json.loads(jar.read(config["refmap"]))
            assert refmap.get("mappings"), "Empty production mixin mappings"
        else:
            assert "refmap" not in config, "Mojang-name runtime should not need a refmap"
        if loader == "fabric":
            metadata = json.loads(jar.read("fabric.mod.json"))
            assert metadata["environment"] == "client"
            assert metadata["depends"]["minecraft"] == version
            assert metadata["id"] == "projects"
            assert "META-INF/mods.toml" not in names
            assert "META-INF/neoforge.mods.toml" not in names
        else:
            metadata_path = "META-INF/neoforge.mods.toml" if loader == "neoforge" else "META-INF/mods.toml"
            metadata = jar.read(metadata_path).decode()
            assert "${" not in metadata, "Unexpanded mod metadata"
            assert f'versionRange="[{version}]"' in metadata
            assert 'modId="projects"' in metadata
            assert "fabric.mod.json" not in names
            if loader == "forge":
                assert "MixinConfigs: projects.mixins.json" in jar.read("META-INF/MANIFEST.MF").decode()
        if version == "1.20.1":
            assert "ServerLevelMixin" in config["mixins"]
            assert "ClientLevelMixin" in config["client"]
        else:
            assert "ServerLevelMixin" not in config["mixins"]
    print(f"OK: {path}")


if __name__ == "__main__":
    if len(sys.argv) < 2:
        raise SystemExit("Usage: python3 scripts/verify_artifact.py <release.jar> [...]")
    for pattern in sys.argv[1:]:
        filenames = glob.glob(pattern)
        if not filenames:
            raise SystemExit(f"No artifacts match: {pattern}")
        for filename in filenames:
            verify(Path(filename))
