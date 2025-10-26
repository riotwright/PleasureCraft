package com.sandymandy.pleasurecraft.settlement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

import java.util.*;

public class SettlementManager extends PersistentState {

    private final Map<UUID, Settlement> settlements = new HashMap<>();

    // === CODEC ===
    public static final Codec<SettlementManager> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Settlement.CODEC)
                    .fieldOf("settlements")
                    .forGetter(manager -> new ArrayList<>(manager.settlements.values()))
    ).apply(instance, list -> {
        SettlementManager manager = new SettlementManager();
        for (Settlement s : list) manager.settlements.put(s.getId(), s);
        return manager;
    }));

    // === PersistentStateType ===
    public static final PersistentStateType<SettlementManager> TYPE = new PersistentStateType<>(
            "pleasurecraft_settlements",
            context -> new SettlementManager(),
            context -> CODEC,
            DataFixTypes.LEVEL
    );

    // === Core Methods ===
    public static SettlementManager get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE);
    }

    public Settlement createSettlement(BlockPos pos, String name, UUID owner) {
        Settlement settlement = new Settlement(UUID.randomUUID(), owner, name, pos);
        settlements.put(settlement.getId(), settlement);
        markDirty();
        return settlement;
    }

    public Settlement getSettlement(UUID id) {
        return settlements.get(id);
    }

    public void removeSettlement(UUID id) {
        settlements.remove(id);
        markDirty();
    }

    public Collection<Settlement> getAllSettlements() {
        return Collections.unmodifiableCollection(settlements.values());
    }

    public void tick(World world) {
        for (Settlement s : settlements.values()) {
            s.tick(world);
        }
    }
}
