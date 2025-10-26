package com.sandymandy.pleasurecraft.block.blocks;

import com.sandymandy.pleasurecraft.block.entity.entities.AbstractBuildingTagBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBuildingTagBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.cuboid(0.0, 3.0/16.0, 14.0/16.0, 1.0, 13.0/16.0, 1.0);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.cuboid(0.0, 3.0/16.0, 0.0,       1.0, 13.0/16.0, 2.0/16.0);
    public static final VoxelShape EAST_SHAPE  = VoxelShapes.cuboid(0.0, 3.0/16.0, 0.0,       2.0/16.0, 13.0/16.0, 1.0);
    public static final VoxelShape WEST_SHAPE  = VoxelShapes.cuboid(14.0/16.0, 3.0/16.0, 0.0, 1.0, 13.0/16.0, 1.0);

    public AbstractBuildingTagBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canMobSpawnInside(BlockState state) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    // --- Directional placement ---
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // face opposite of player direction (like furnaces)
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(Properties.HORIZONTAL_FACING)) {
              case SOUTH -> SOUTH_SHAPE;
            case EAST  -> EAST_SHAPE;
            case WEST  -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }


    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof AbstractBuildingTagBlockEntity tag) {
            tag.onBreak();
        }
        super.onBroken(world, pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;

        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof AbstractBuildingTagBlockEntity tag) {
            return tag.onInteract(player, world, pos);
        }

        return ActionResult.FAIL;
    }
}