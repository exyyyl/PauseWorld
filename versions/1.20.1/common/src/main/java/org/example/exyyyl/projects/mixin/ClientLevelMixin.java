package org.example.exyyyl.projects.mixin;

import java.util.function.BooleanSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.example.exyyyl.projects.PauseState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void worldpause$freezeTime(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        if (PauseState.isPaused((ClientLevel) (Object) this)) {
            ci.cancel();
        }
    }

    @Inject(method = "tickNonPassenger", at = @At("HEAD"), cancellable = true)
    private void worldpause$freezeEntity(Entity entity, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        if (PauseState.isPaused(entity.level()) && !(entity instanceof Player)
            && (player == null || entity != player.getRootVehicle())) {
            ci.cancel();
        }
    }
}
