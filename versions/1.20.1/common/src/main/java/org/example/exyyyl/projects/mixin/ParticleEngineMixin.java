package org.example.exyyyl.projects.mixin;

import net.minecraft.client.particle.ParticleEngine;
import org.example.exyyyl.projects.PauseState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void worldpause$freezeParticles(CallbackInfo ci) {
        if (PauseState.isTimeStopped()) {
            ci.cancel();
        }
    }
}
