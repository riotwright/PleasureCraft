package com.sandymandy.pleasurecraft.screen;

import com.sandymandy.pleasurecraft.PleasureCraft;
import com.sandymandy.pleasurecraft.settlement.Settlement;
import com.sandymandy.pleasurecraft.settlement.SettlementResourceData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class SettlementHubScreenHandlerFactory implements ExtendedScreenHandlerFactory {
    private final Settlement data;

    public SettlementHubScreenHandlerFactory(Settlement data) {
        this.data = data;
    }

    // Called on the server → sends data to client
    @Override
    public Object getScreenOpeningData(ServerPlayerEntity player) {
        PleasureCraft.LOGGER.info(data+"");
        return data;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.pleasurecraft.settlement_hub");
    }

    // Called on the client
    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SettlementHubScreenHandler(syncId, playerInventory, data);
    }
}
