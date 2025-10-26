package com.sandymandy.pleasurecraft.block.blocks;

import com.mojang.serialization.MapCodec;
import com.sandymandy.pleasurecraft.block.entity.PleasureCraftBlockEntities;
import com.sandymandy.pleasurecraft.block.entity.entities.SettlementHubBlockEntity;
import com.sandymandy.pleasurecraft.settlement.SettlementManager;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SettlementHubBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<SettlementHubBlock> CODEC = SettlementHubBlock.createCodec(SettlementHubBlock::new);

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        if (world instanceof ServerWorld serverWorld && placer instanceof PlayerEntity player) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof SettlementHubBlockEntity hub) {
                hub.initializeWithOwner(serverWorld, player.getUuid());
            }
        }
    }

    public SettlementHubBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canMobSpawnInside(BlockState state) {
        return false;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SettlementHubBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world, BlockState state, BlockEntityType<T> type) {

        return validateTicker(type, PleasureCraftBlockEntities.SETTLEMENT_HUB_BLOCK_ENTITY,
                SettlementHubBlockEntity::tick);
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (world instanceof ServerWorld serverWorld) {
            SettlementManager manager = SettlementManager.get(serverWorld);
            manager.getAllSettlements().stream()
                    .filter(s -> s.getCorePos().equals(pos))
                    .findFirst()
                    .ifPresent(s -> manager.removeSettlement(s.getId()));
        }
        super.onBroken(world, pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;

        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof SettlementHubBlockEntity hub) {
            hub.openGui((ServerWorld) world, (ServerPlayerEntity) player);
        }

        return ActionResult.CONSUME;
    }


}
