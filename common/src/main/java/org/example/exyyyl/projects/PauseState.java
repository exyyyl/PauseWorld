package org.example.exyyyl.projects;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;

/** Server identity prevents a paused world from affecting another world. */
public final class PauseState {
    private static volatile MinecraftServer pausedServer;

    private PauseState() {}

    public static boolean isTimeStopped() {
        return pausedServer != null;
    }

    public static boolean isPaused(Level level) {
        MinecraftServer server = pausedServer;
        return server != null && (level.isClientSide || level.getServer() == server);
    }

    public static void pause(MinecraftServer server) {
        pausedServer = server;
    }

    public static void clear(MinecraftServer server) {
        if (pausedServer == server) {
            pausedServer = null;
        }
    }
}
