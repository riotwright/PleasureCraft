package com.sandymandy.pleasurecraft.util.variables;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public enum AIMode {
    SETTLEMENT,
    WILDERNESS,
    SCENE;

    public static final PacketCodec<ByteBuf, AIMode> PACKET_CODEC = PacketCodecs.indexed(
            i -> AIMode.values()[i],  // Decode: int ordinal -> enum
            AIMode::ordinal           // Encode: enum -> int ordinal
    );
}