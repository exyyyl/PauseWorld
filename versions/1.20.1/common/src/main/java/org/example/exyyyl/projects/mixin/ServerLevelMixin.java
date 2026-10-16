package org.example.exyyyl.projects.mixin;

import java.util.function.BooleanSupplier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.example.exyyyl.projects.PauseState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void worldpause$freezeWorld(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (!PauseState.isPaused(level)) {
            return;
        }
        // Keep chunk loading and the player responsive; skip world simulation.
        level.getChunkSource().tick(hasTimeLeft, false);
        for (ServerPlayer player : level.players()) {
            if (!player.isRemoved()) {
                level.tickNonPassenger(player.getRootVehicle());
            }
        }
        ci.cancel();
    }
}
