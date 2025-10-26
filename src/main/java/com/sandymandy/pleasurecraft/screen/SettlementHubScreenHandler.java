package com.sandymandy.pleasurecraft.screen;

import com.sandymandy.pleasurecraft.settlement.Settlement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

import static com.sandymandy.pleasurecraft.registries.PleasureCraftScreenHandlerRegistry.SETTLEMENT_HUB_SCREEN_HANDLER;

public class SettlementHubScreenHandler extends ScreenHandler {
    private final Settlement data;

    public SettlementHubScreenHandler(int syncId, PlayerInventory playerInventory, Settlement data) {
        super(SETTLEMENT_HUB_SCREEN_HANDLER, syncId); // will register later
        this.data = data;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public Settlement getData() {
        return data;
    }
}
