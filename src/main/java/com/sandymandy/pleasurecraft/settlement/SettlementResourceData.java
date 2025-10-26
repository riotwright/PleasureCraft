package com.sandymandy.pleasurecraft.settlement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public record SettlementResourceData(
        float morale,
        int food,
        int materials,
        int settlementTokens
) {
    public static final SettlementResourceData DEFAULT = new SettlementResourceData(1.0f, 100, 0, 0);

    // === CODEC ===
    public static final Codec<SettlementResourceData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("morale").orElse(1.0f).forGetter(SettlementResourceData::morale),
            Codec.INT.fieldOf("food").orElse(100).forGetter(SettlementResourceData::food),
            Codec.INT.fieldOf("materials").orElse(0).forGetter(SettlementResourceData::materials),
            Codec.INT.fieldOf("settlement_tokens").orElse(0).forGetter(SettlementResourceData::settlementTokens)
    ).apply(instance, SettlementResourceData::new));

    // === PACKET_CODEC ===
    public static final PacketCodec<RegistryByteBuf, SettlementResourceData> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public SettlementResourceData decode(RegistryByteBuf buf) {
            return new SettlementResourceData(
                    buf.readFloat(),
                    buf.readVarInt(),
                    buf.readVarInt(),
                    buf.readVarInt()
            );
        }

        @Override
        public void encode(RegistryByteBuf buf, SettlementResourceData data) {
            buf.writeFloat(data.morale());
            buf.writeVarInt(data.food());
            buf.writeVarInt(data.materials());
            buf.writeVarInt(data.settlementTokens());
        }
    };

    public SettlementResourceData withMorale(float morale) {
        return new SettlementResourceData(morale, food, materials, settlementTokens);
    }

    public SettlementResourceData withFood(int food) {
        return new SettlementResourceData(morale, food, materials, settlementTokens);
    }

    public SettlementResourceData withMaterials(int materials) {
        return new SettlementResourceData(morale, food, materials, settlementTokens);
    }

    public SettlementResourceData withSettlementTokens(int population) {
        return new SettlementResourceData(morale, food, materials, population);
    }
}
