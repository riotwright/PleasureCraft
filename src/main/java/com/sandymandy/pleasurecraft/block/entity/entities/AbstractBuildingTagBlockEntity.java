package com.sandymandy.pleasurecraft.block.entity.entities;

import com.sandymandy.pleasurecraft.settlement.Settlement;
import com.sandymandy.pleasurecraft.settlement.building.BuildingType;
import com.sandymandy.pleasurecraft.util.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.UUID;

import static com.sandymandy.pleasurecraft.block.entity.PleasureCraftBlockEntities.BUILDING_TAG_BLOCK_ENTITY;

public class AbstractBuildingTagBlockEntity extends BlockEntity {

    private BuildingType buildingType = BuildingType.NONE;
    private UUID buildingId = UUID.randomUUID();
    private Settlement settlement = null;

    public AbstractBuildingTagBlockEntity(BlockPos pos, BlockState state, BuildingType type) {
        super(BUILDING_TAG_BLOCK_ENTITY, pos, state);
        if (type != null) this.buildingType = type;
    }

    // --- Getters / Setters ---
    public BuildingType getBuildingType() {
        return buildingType;
    }

    public UUID getBuildingId() {
        return buildingId;
    }

    // --- Saving / Loading ---
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.put("BuildingType", BuildingType.CODEC, buildingType);
        nbt.put("BuildingId", Uuids.CODEC, buildingId);
        if(settlement != null) nbt.put("Settlement", Settlement.CODEC, settlement);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        if (nbt.contains("BuildingType")) this.buildingType = nbt.get("BuildingType", BuildingType.CODEC).get();
        if (nbt.contains("BuildingId")) this.buildingId = nbt.get("BuildingId", Uuids.CODEC).get();
        if (nbt.contains("Settlement")) this.settlement = nbt.get("Settlement", Settlement.CODEC).get();
    }

    // Optional tick method
    public static void tick(World world, BlockPos pos, BlockState state, AbstractBuildingTagBlockEntity be) {
        // override in subclasses for special behavior
    }

    public ActionResult onInteract(PlayerEntity player, World world, BlockPos pos){
        Settlement nearestSettlement = Utils.findNearestSettlement(world, pos);
        Direction facingDirection = world.getBlockState(pos).get(Properties.HORIZONTAL_FACING);
        BlockPos doorPos = Utils.findNearbyDoor(world, pos, facingDirection);

        if (doorPos == null) {
            player.sendMessage(Text.literal("§cYou must place this tag above or beside a door!"), true);
            return ActionResult.FAIL;
        }

        if(nearestSettlement == null){
            player.sendMessage(Text.of("§cNo near by settlements found"), true);
            return ActionResult.FAIL;
        }

        if(!nearestSettlement.getOwner().equals(player.getUuid())){
            player.sendMessage(Text.of("§cNo near by settlements found owned by you"), true);
            return ActionResult.FAIL;
        }

        player.sendMessage(Text.of("§aRegistering to " + nearestSettlement.getName()), true);
        nearestSettlement.registerBuilding(world, this.getBuildingId(), doorPos, facingDirection, pos, this.getBuildingType(), player);
        this.settlement = nearestSettlement;
        return ActionResult.CONSUME;
    }

    public void onBreak(){
        if(settlement != null){
            settlement.removeBuilding(this.getBuildingId());
        }
    }



}
