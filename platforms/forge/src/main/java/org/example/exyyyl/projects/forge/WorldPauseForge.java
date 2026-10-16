package org.example.exyyyl.projects.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.example.exyyyl.projects.ModKeyBindings;
import org.example.exyyyl.projects.Projects;

@Mod(Projects.MODID)
public final class WorldPauseForge {
    @Mod.EventBusSubscriber(modid = Projects.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static final class ClientEvents {
        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBindings.TIME_STOP);
        }
    }
}
