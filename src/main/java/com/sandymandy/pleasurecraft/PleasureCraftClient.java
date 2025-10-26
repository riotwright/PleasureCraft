package com.sandymandy.pleasurecraft;


import com.sandymandy.pleasurecraft.client.PleasureCraftKeybinds;
import com.sandymandy.pleasurecraft.client.renderers.LucyRenderer;
import com.sandymandy.pleasurecraft.client.renderers.MikaRenderer;
import com.sandymandy.pleasurecraft.client.renderers.MomoRenderer;
import com.sandymandy.pleasurecraft.config.ModBindings;
import com.sandymandy.pleasurecraft.config.ModConfig;
import com.sandymandy.pleasurecraft.freecam.Freecam;
import com.sandymandy.pleasurecraft.networking.C2S.CumKeybindC2SPacket;
import com.sandymandy.pleasurecraft.networking.C2S.ThrustKeybindC2SPacket;
import com.sandymandy.pleasurecraft.networking.PleasureCraftClientPackets;
import com.sandymandy.pleasurecraft.registries.GirlRegistry;
import com.sandymandy.pleasurecraft.registries.PleasureCraftHudRegistry;
import com.sandymandy.pleasurecraft.client.gui.screen.GirlInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import com.sandymandy.pleasurecraft.client.gui.screen.settlement.SettlementHubScreen;

import static com.sandymandy.pleasurecraft.registries.PleasureCraftScreenHandlerRegistry.GIRL_INVENTORY_SCREEN_HANDLER;
import static com.sandymandy.pleasurecraft.registries.PleasureCraftScreenHandlerRegistry.SETTLEMENT_HUB_SCREEN_HANDLER;


public class PleasureCraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModConfig.init();
        ModBindings.forEach(KeyBindingHelper::registerKeyBinding);
        ClientTickEvents.START_CLIENT_TICK.register(Freecam::preTick);
        ClientTickEvents.END_CLIENT_TICK.register(Freecam::postTick);
        HandledScreens.register(GIRL_INVENTORY_SCREEN_HANDLER, GirlInventoryScreen::new);
        HandledScreens.register(SETTLEMENT_HUB_SCREEN_HANDLER, SettlementHubScreen::new);

        EntityRendererRegistry.register(GirlRegistry.LUCY, LucyRenderer::new);
        EntityRendererRegistry.register(GirlRegistry.MIKA, MikaRenderer::new);
        EntityRendererRegistry.register(GirlRegistry.MOMO, MomoRenderer::new);
        PleasureCraftKeybinds.register();
        PleasureCraftClientPackets.registerS2CPackets();
        PleasureCraftHudRegistry.register();
        handleKeybinds();
    }

    private static void handleKeybinds() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Thrust button held
            boolean thrustHeld = PleasureCraftKeybinds.thrustKey.isPressed();
            ClientPlayNetworking.send(new ThrustKeybindC2SPacket(thrustHeld));

            // Cum button (pressed once)
            ClientPlayNetworking.send(new CumKeybindC2SPacket(PleasureCraftKeybinds.cumKey.wasPressed()));

        });
    }

    public record GirlScreenData(int entityId) {
        public static final PacketCodec<RegistryByteBuf, GirlScreenData> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.VAR_INT,
                GirlScreenData::entityId,
                GirlScreenData::new
        );
    }

}
