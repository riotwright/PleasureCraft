package com.sandymandy.pleasurecraft.block.entity.entities;

import com.sandymandy.pleasurecraft.block.entity.PleasureCraftBlockEntities;
import com.sandymandy.pleasurecraft.screen.SettlementHubScreenHandlerFactory;
import com.sandymandy.pleasurecraft.settlement.Settlement;
import com.sandymandy.pleasurecraft.settlement.SettlementManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SettlementHubBlockEntity extends BlockEntity {
    private Settlement settlement;

    public SettlementHubBlockEntity(BlockPos pos, BlockState state) {
        super(PleasureCraftBlockEntities.SETTLEMENT_HUB_BLOCK_ENTITY, pos, state);
    }

    /* === Networking & Sync === */

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    /* === GUI Handling === */

    public void openGui(ServerWorld world, ServerPlayerEntity player) {
        if (settlement != null) {
            PlayerEntity owner = world.getPlayerByUuid(settlement.getOwner());
            if(player.equals(owner)) {
                player.openHandledScreen(new SettlementHubScreenHandlerFactory(settlement));
            }
            else {
                player.sendMessage(Text.of("§cThis is not owned by you"), true);
            }
        }
    }

    /* === Tick === */

    public static void tick(World world, BlockPos pos, BlockState state, SettlementHubBlockEntity be) {

        if (world.isClient()) return;

        if (be.settlement != null) {
            be.settlement.tick(world);
        }
    }



    /* === Setup === */

    public void initializeWithOwner(ServerWorld world, UUID ownerId) {
        SettlementManager manager = SettlementManager.get(world);

        if (this.settlement == null) {
            PlayerEntity owner = world.getPlayerByUuid(ownerId);
            String name = "Settlement@" + getPos().toShortString();

            if(owner != null) name = owner.getName().getString().replace("literal{","").replace("}","") + "'s Settlement";

            this.settlement = manager.createSettlement(getPos(), name, ownerId);
            markDirty();
        }
    }

    /* === Persistence === */

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (settlement != null) {
            nbt.put("SettlementId", Uuids.CODEC, settlement.getId());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains("SettlementId")) {
            UUID id = nbt.get("SettlementId", Uuids.CODEC).get();
            if (world instanceof ServerWorld serverWorld) {
                SettlementManager manager = SettlementManager.get(serverWorld);
                this.settlement = manager.getSettlement(id);
            }
        }
    }

    /* === Sync Utility === */

    public void syncToClient() {
        if (world != null && !world.isClient()) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
            markDirty();
        }
    }

    public Settlement getSettlement() {
        return settlement;
    }
}
