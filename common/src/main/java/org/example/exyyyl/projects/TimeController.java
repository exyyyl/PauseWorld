package org.example.exyyyl.projects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;

public final class TimeController {
    // The client replaces this identity on disconnect. Queued server work checks it.
    private static volatile Session session;

    private TimeController() {}

    public static boolean isTimeStopped() {
        return PauseState.isTimeStopped();
    }

    public static void clientTick() {
        Minecraft mc = Minecraft.getInstance();
        IntegratedServer server = mc.getSingleplayerServer();
        if (mc.player == null || server == null || server.isPublished()) {
            restoreAndReset();
        } else if (session == null || session.server != server) {
            restoreAndReset();
            session = new Session(server);
        }

        while (ModKeyBindings.TIME_STOP.consumeClick()) {
            if (mc.screen == null && session != null) {
                toggleTimeStop();
            }
        }
    }

    public static void toggleTimeStop() {
        Session requested = session;
        if (requested == null) {
            return;
        }
        // World simulation belongs to the server thread, even in singleplayer.
        requested.server.execute(() -> {
            final boolean stopped;
            synchronized (TimeController.class) {
                if (session != requested || requested.server.isPublished()
                    || requested.server.getPlayerCount() != 1) {
                    return;
                }
                if (requested.paused) {
                    requested.release();
                } else {
                    requested.previouslyFrozen = FreezeBackend.isFrozen(requested.server);
                    FreezeBackend.setFrozen(requested.server, true);
                    requested.paused = true;
                    PauseState.pause(requested.server);
                }
                stopped = requested.paused;
            }
            Minecraft.getInstance().execute(() -> {
                if (session != requested || requested.paused != stopped) {
                    return;
                }
                Minecraft mc = Minecraft.getInstance();
                if (stopped) {
                    mc.getSoundManager().pause();
                } else {
                    mc.getSoundManager().resume();
                }
                if (mc.player != null) {
                    mc.player.displayClientMessage(Component.translatable(
                        stopped ? "message.projects.time_stopped" : "message.projects.time_resumed"
                    ), true);
                }
            });
        });
    }

    public static synchronized void restoreAndReset() {
        Session previous = session;
        session = null;
        if (previous != null) {
            PauseState.clear(previous.server);
            previous.server.execute(previous::release);
            Minecraft.getInstance().getSoundManager().resume();
        }
    }

    private static final class Session {
        private final IntegratedServer server;
        private volatile boolean paused;
        private boolean previouslyFrozen;

        private Session(IntegratedServer server) {
            this.server = server;
        }

        private void release() {
            if (paused) {
                FreezeBackend.setFrozen(server, previouslyFrozen);
                paused = false;
                PauseState.clear(server);
            }
        }
    }
}
