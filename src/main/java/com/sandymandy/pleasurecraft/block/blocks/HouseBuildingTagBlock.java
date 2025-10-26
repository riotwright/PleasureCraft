package com.sandymandy.pleasurecraft.block.blocks;

import com.mojang.serialization.MapCodec;
import com.sandymandy.pleasurecraft.block.entity.entities.AbstractBuildingTagBlockEntity;
import com.sandymandy.pleasurecraft.settlement.building.BuildingType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class HouseBuildingTagBlock extends AbstractBuildingTagBlock{
    public static final MapCodec<HouseBuildingTagBlock> CODEC = HouseBuildingTagBlock.createCodec(HouseBuildingTagBlock::new);

    public HouseBuildingTagBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AbstractBuildingTagBlockEntity(pos, state, BuildingType.HOUSE);
    }
}
