package org.example.exyyyl.projects.mixin;

import net.minecraft.client.Minecraft;
import org.example.exyyyl.projects.TimeController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class WorldExitMixin {
    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screens/Screen;Z)V", at = @At("HEAD"))
    private void worldpause$leaveWorld(CallbackInfo ci) {
        TimeController.restoreAndReset();
    }
}
