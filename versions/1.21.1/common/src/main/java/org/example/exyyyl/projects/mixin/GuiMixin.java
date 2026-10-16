package org.example.exyyyl.projects.mixin;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import org.example.exyyyl.projects.client.TimeStopOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void worldpause$renderOverlay(GuiGraphics graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        TimeStopOverlay.render(graphics);
    }
}
