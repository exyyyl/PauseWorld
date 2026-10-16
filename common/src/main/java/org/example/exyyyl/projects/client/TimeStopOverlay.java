package org.example.exyyyl.projects.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.example.exyyyl.projects.TimeController;

public final class TimeStopOverlay {
    private static final ItemStack CLOCK = new ItemStack(Items.CLOCK);

    private TimeStopOverlay() {}

    public static void render(GuiGraphics graphics) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui || !TimeController.isTimeStopped()) {
            return;
        }
        graphics.pose().pushPose();
        graphics.pose().translate((graphics.guiWidth() - 24) / 2.0F, 10, 0);
        graphics.pose().scale(1.5F, 1.5F, 1.0F);
        graphics.renderFakeItem(CLOCK, 0, 0);
        graphics.pose().popPose();
    }
}
