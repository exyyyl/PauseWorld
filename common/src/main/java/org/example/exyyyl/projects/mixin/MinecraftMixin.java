package org.example.exyyyl.projects.mixin;

import net.minecraft.client.Minecraft;
import org.example.exyyyl.projects.TimeController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void worldpause$clientTick(CallbackInfo ci) {
        TimeController.clientTick();
    }

    @Inject(method = "setScreen", at = @At("TAIL"))
    private void worldpause$screenChanged(CallbackInfo ci) {
        if (TimeController.isTimeStopped()) {
            Minecraft.getInstance().getSoundManager().pause();
        }
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void worldpause$shutdown(CallbackInfo ci) {
        TimeController.restoreAndReset();
    }
}
