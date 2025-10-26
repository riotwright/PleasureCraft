package com.sandymandy.pleasurecraft.block.entity;

import com.sandymandy.pleasurecraft.PleasureCraft;
import com.sandymandy.pleasurecraft.block.PleasureCraftBlocks;
import com.sandymandy.pleasurecraft.block.entity.entities.AbstractBuildingTagBlockEntity;
import com.sandymandy.pleasurecraft.block.entity.entities.SettlementHubBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PleasureCraftBlockEntities {
    public static BlockEntityType<SettlementHubBlockEntity> SETTLEMENT_HUB_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(PleasureCraft.MOD_ID, "settlement_hub"),
                    FabricBlockEntityTypeBuilder.create(SettlementHubBlockEntity::new, PleasureCraftBlocks.SETTLEMENT_HUB).build()
    );

    public static BlockEntityType<AbstractBuildingTagBlockEntity> BUILDING_TAG_BLOCK_ENTITY =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(PleasureCraft.MOD_ID, "building_tag"),
                    FabricBlockEntityTypeBuilder.create(
                            (pos, state) -> new AbstractBuildingTagBlockEntity(pos, state, null),
                            PleasureCraftBlocks.HOUSE_BUILDING_TAG_BLOCK
                    ).build()
            );

    public static void registerBlockEntities() {
        PleasureCraft.LOGGER.info("Registering Block Entities for PleasureCraft");
    }
}