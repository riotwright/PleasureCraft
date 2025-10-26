package com.sandymandy.pleasurecraft.settlement.building;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public enum BuildingType {
    HOUSE,
    STORAGE,
    BLACKSMITH,
    FARM,
    BROTHEL,
    NONE;

    public static final PacketCodec<ByteBuf, BuildingType> PACKET_CODEC = PacketCodecs.indexed(
            i -> BuildingType.values()[i],  // Decode: int ordinal -> enum
            BuildingType::ordinal           // Encode: enum -> int ordinal
    );

    public static final Codec<BuildingType> CODEC = Codec.STRING.xmap(
            name -> {
                try {
                    return BuildingType.valueOf(name.toUpperCase());
                } catch (IllegalArgumentException e) {
                    return BuildingType.NONE; // fallback if unknown
                }
            },
            type -> type.name().toLowerCase()
    );

    public String getTranslationKey() {
        return "building.pleasurecraft." + name().toLowerCase();
    }
}