package com.sandymandy.pleasurecraft.util;

import com.sandymandy.pleasurecraft.settlement.Settlement;
import com.sandymandy.pleasurecraft.settlement.SettlementManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

public class Utils {

    public static Settlement findNearestSettlement(World world, BlockPos pos) {
        if (!(world instanceof ServerWorld serverWorld)) return null;

        SettlementManager manager = SettlementManager.get(serverWorld);

        return manager.getAllSettlements().stream()
                .filter(s -> s.getCorePos().isWithinDistance(pos, 200))
                .min(Comparator.comparingDouble(s -> s.getCorePos().getSquaredDistance(pos)))
                .orElse(null);
    }

    public static BlockPos findNearbyDoor(World world, BlockPos origin, Direction facing) {
        BlockPos check = getBlockBehind(origin, facing);
        BlockPos result = null;

        // Check directly below (tag above upper door half)
        BlockPos below = check.down();
        if (world.getBlockState(below).isIn(BlockTags.DOORS)) {
            result = below;
        }

        // Check horizontally around
        for (Direction dir : Direction.Type.HORIZONTAL) {
            BlockPos side = check.offset(dir);
            BlockState state = world.getBlockState(side);

            // If the side block itself is a door
            if (state.isIn(BlockTags.DOORS)) {
                result = side;
            }

            // Or if the door is one below that (tag placed one higher on the wall)
            BlockPos sideBelow = side.down();
            if (world.getBlockState(sideBelow).isIn(BlockTags.DOORS)) {
                result = sideBelow;
            }

            // Or one above (in case tag placed at lower door half)
            BlockPos sideAbove = side.up();
            if (world.getBlockState(sideAbove).isIn(BlockTags.DOORS)) {
                result = sideAbove;
            }
        }

        if(result != null){
            if(world.getBlockState(result).get(Properties.DOUBLE_BLOCK_HALF).equals(DoubleBlockHalf.UPPER)){
                return new BlockPos(result.getX(), result.getY() - 1, result.getZ());
            }
        }

        return result;
    }

    public static BlockPos getBlockBehind(BlockPos origin, Direction dir){
        if(dir.equals(Direction.SOUTH)){
            return new BlockPos(origin.getX(), origin.getY(), origin.getZ() - 1);
        }

        if(dir.equals(Direction.EAST)){
            return new BlockPos(origin.getX() - 1, origin.getY(), origin.getZ());
        }

        if(dir.equals(Direction.WEST)){
            return new BlockPos(origin.getX() + 1, origin.getY(), origin.getZ());
        }

        return new BlockPos(origin.getX(), origin.getY(), origin.getZ() + 1);

    }

    public static BlockInfo findNearbyBlock(World world, BlockPos center, int radius, @Nullable Block block, @Nullable TagKey<Block> blockTag) {
        for (BlockPos pos : BlockPos.iterate(
                center.add(-radius, -radius, -radius),
                center.add(radius, radius, radius))) {

            BlockState state = world.getBlockState(pos);

            if (isBlockOrTag(state, block, blockTag)) {
                // Found a matching block, collect info
                Direction facing = state.contains(Properties.HORIZONTAL_FACING)
                        ? state.get(Properties.HORIZONTAL_FACING)
                        : Direction.NORTH; // fallback

                return new BlockInfo(pos.toImmutable(), state, facing);
            }
        }
        return null; // none found
    }

    public static boolean checkForBlockAt(World world, BlockPos blockPos, @Nullable Block block, @Nullable TagKey<Block> blockTag){
        BlockState state = world.getBlockState(blockPos);
        return isBlockOrTag(state, block, blockTag);
    }

    private static boolean isBlockOrTag(BlockState state, @Nullable Block block, @Nullable TagKey<Block> tag) {
        if (block != null && state.isOf(block)) {
            return true;
        }
        return tag != null && state.isIn(tag);
    }

    public static float Round(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace, RoundingMode.HALF_DOWN).floatValue();

    }

    public static int withFullAlpha(int color) {
        // If already has an alpha byte (ARGB)
        if ((color & 0xFF000000) != 0) {
            return color;
        }
        // Add FF as the alpha (shift by 24 bits)
        return 0xFF000000 | color;
    }

    public static String getReadableTameItemName(Item tameItem) {
        Identifier id = Registries.ITEM.getId(tameItem);

        if (id != null) {
            String path = id.getPath(); // e.g., "blue_allium"

            // Capitalize each word split by underscores
            String[] words = path.split("_");
            StringBuilder formatted = new StringBuilder();

            for (String word : words) {
                if (!word.isEmpty()) {
                    formatted.append(Character.toUpperCase(word.charAt(0)))
                            .append(word.substring(1))
                            .append(" ");
                }
            }

            return formatted.toString().trim(); // "Blue Allium"
        } else {
            return "Unknown Item";
        }
    }

    // simple record to hold info
    public record BlockInfo(BlockPos pos, BlockState state, Direction facing) {}


}
