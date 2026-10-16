package org.example.exyyyl.projects.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.example.exyyyl.projects.ModKeyBindings;
import org.example.exyyyl.projects.Projects;

@Mod(Projects.MODID)
public final class WorldPauseNeoForge {
    @EventBusSubscriber(modid = Projects.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static final class ClientEvents {
        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBindings.TIME_STOP);
        }
    }
}
