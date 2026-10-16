package org.example.exyyyl.projects.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.example.exyyyl.projects.ModKeyBindings;

public final class WorldPauseFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(ModKeyBindings.TIME_STOP);
    }
}
