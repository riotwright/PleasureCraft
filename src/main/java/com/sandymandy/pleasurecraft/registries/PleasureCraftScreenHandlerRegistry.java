package com.sandymandy.pleasurecraft.registries;

import com.sandymandy.pleasurecraft.PleasureCraft;
import com.sandymandy.pleasurecraft.PleasureCraftClient;
import com.sandymandy.pleasurecraft.screen.GirlInventoryScreenHandler;
import com.sandymandy.pleasurecraft.screen.SettlementHubScreenHandler;
import com.sandymandy.pleasurecraft.settlement.Settlement;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class PleasureCraftScreenHandlerRegistry {
    public static final ExtendedScreenHandlerType<GirlInventoryScreenHandler, PleasureCraftClient.GirlScreenData> GIRL_INVENTORY_SCREEN_HANDLER =
            Registry.register(
                    Registries.SCREEN_HANDLER,
                    Identifier.of(PleasureCraft.MOD_ID, "girl_inventory_screen"),
                    new ExtendedScreenHandlerType<>(GirlInventoryScreenHandler::new, PleasureCraftClient.GirlScreenData.PACKET_CODEC)
            );

    public static final ScreenHandlerType<SettlementHubScreenHandler> SETTLEMENT_HUB_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER,
                    Identifier.of(PleasureCraft.MOD_ID, "settlement_hub"),
                    new ExtendedScreenHandlerType<>(SettlementHubScreenHandler::new, Settlement.PACKET_CODEC));


    public static void register(){
        PleasureCraft.LOGGER.info("Registering Screen Handlers for PleasureCraft");
    }
}
