package com.sandymandy.pleasurecraft.settlement.building;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.UUID;

public class SettlementBuilding {
//    private final UUID buildingID;
    private final BlockPos doorPos;
    private final BlockPos tagPos;
    private final BuildingType buildingType; // e.g. pleasurecraft:house
    private final List<BlockPos> structureBlocks; // all connected blocks

    public static final Codec<SettlementBuilding> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//            Uuids.CODEC.fieldOf("doorPos").forGetter(SettlementBuilding::getID),
            BlockPos.CODEC.fieldOf("doorPos").forGetter(SettlementBuilding::getDoorPos),
            BlockPos.CODEC.fieldOf("tagPos").forGetter(SettlementBuilding::getTagPos),
            BuildingType.CODEC.fieldOf("buildingType").forGetter(SettlementBuilding::getBuildingType),
            Codec.list(BlockPos.CODEC).fieldOf("structureBlocks").forGetter(SettlementBuilding::getStructureBlocks)
    ).apply(instance, SettlementBuilding::new));

    public SettlementBuilding(/*UUID buildingID, */BlockPos doorPos, BlockPos tagPos, BuildingType buildingType, List<BlockPos> structureBlocks) {
//        this.buildingID = buildingID;
        this.doorPos = doorPos;
        this.tagPos = tagPos;
        this.buildingType = buildingType;
        this.structureBlocks = structureBlocks;
    }

//    public UUID getID() { return buildingID; }
    public BlockPos getDoorPos() { return doorPos; }
    public BlockPos getTagPos() { return tagPos; }
    public BuildingType getBuildingType() { return buildingType; }
    public List<BlockPos> getStructureBlocks() { return structureBlocks; }
}
