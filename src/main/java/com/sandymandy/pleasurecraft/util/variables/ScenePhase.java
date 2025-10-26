package com.sandymandy.pleasurecraft.util.variables;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public enum ScenePhase {
    NONE,
    DIALOG,
    LAYING_DOWN,
    BED_IDLE,
    INTRO,
    MOVING,
    HAVING_SEX,
    CUM;

    /**
     * A PacketCodec for encoding/decoding ScenePhase values over the network.
     * Uses ordinal indexing for efficiency (like vanilla enums).
     */
    public static final PacketCodec<ByteBuf, ScenePhase> PACKET_CODEC = PacketCodecs.indexed(
            i -> ScenePhase.values()[i],  // Decode: int ordinal -> enum
            ScenePhase::ordinal           // Encode: enum -> int ordinal
    );
}