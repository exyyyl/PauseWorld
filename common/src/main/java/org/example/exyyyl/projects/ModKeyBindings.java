package org.example.exyyyl.projects;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public final class ModKeyBindings {
    public static final KeyMapping TIME_STOP = new KeyMapping(
        "key.projects.time_stop", InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_GRAVE_ACCENT, "key.category.projects"
    );

    private ModKeyBindings() {}
}
