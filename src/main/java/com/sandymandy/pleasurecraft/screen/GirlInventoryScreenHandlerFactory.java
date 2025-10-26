package com.sandymandy.pleasurecraft.screen;

import com.sandymandy.pleasurecraft.PleasureCraftClient;
import com.sandymandy.pleasurecraft.entity.base.TameableGirlEntity;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class GirlInventoryScreenHandlerFactory implements ExtendedScreenHandlerFactory {
    private final TameableGirlEntity girl;

    public GirlInventoryScreenHandlerFactory(TameableGirlEntity girl) {
        this.girl = girl;
    }

    // Called on the server → sends data to client
    @Override
    public Object getScreenOpeningData(ServerPlayerEntity player) {
        return new PleasureCraftClient.GirlScreenData(girl.getId());
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("");
    }

    // Called on the client
    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GirlInventoryScreenHandler(syncId, playerInventory, girl.getId());
    }
}
