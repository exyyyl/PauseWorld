package org.example.exyyyl.projects;

import net.minecraft.server.MinecraftServer;

/** 1.20.1 has no tick-rate manager; its mixins consult PauseState instead. */
public final class FreezeBackend {
    private FreezeBackend() {}

    public static boolean isFrozen(MinecraftServer server) {
        return false;
    }

    public static void setFrozen(MinecraftServer server, boolean frozen) {
        // ServerLevelMixin and ClientLevelMixin implement the legacy freeze.
    }
}
