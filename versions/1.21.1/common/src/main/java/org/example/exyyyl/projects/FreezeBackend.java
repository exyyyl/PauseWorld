package org.example.exyyyl.projects;

import net.minecraft.server.MinecraftServer;

public final class FreezeBackend {
    private FreezeBackend() {}

    public static boolean isFrozen(MinecraftServer server) {
        return server.tickRateManager().isFrozen();
    }

    public static void setFrozen(MinecraftServer server, boolean frozen) {
        server.tickRateManager().setFrozen(frozen);
    }
}
