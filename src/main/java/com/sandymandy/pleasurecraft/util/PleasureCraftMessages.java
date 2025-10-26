package com.sandymandy.pleasurecraft.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Objects;

public class PleasureCraftMessages {
    public static void GlobleMessage(World world, String messageContent) {
        Text message = Text.literal(messageContent);
        if (world.isClient) return; // Don't run on client


        Objects.requireNonNull(world.getServer())
                .getPlayerManager()
                .broadcast(message, false);
    }

    public static void PlayerSpecificMessage(PlayerEntity playerEntity, String messageContent){
        Text message = Text.literal(messageContent);
        playerEntity.sendMessage(message,false);
    }

}
