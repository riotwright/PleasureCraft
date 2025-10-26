package com.sandymandy.pleasurecraft.block;

import com.sandymandy.pleasurecraft.PleasureCraft;
import com.sandymandy.pleasurecraft.block.blocks.HouseBuildingTagBlock;
import com.sandymandy.pleasurecraft.block.blocks.SettlementHubBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class PleasureCraftBlocks {
    public static Block SETTLEMENT_HUB = registerBlock("settlement_hub",
            properties -> new SettlementHubBlock(properties.strength(1.0F, 1200.0F).sounds(BlockSoundGroup.LODESTONE).requiresTool()));

    public static Block HOUSE_BUILDING_TAG_BLOCK = registerBlock("house_tag",
            properties -> new HouseBuildingTagBlock(properties.strength(1.0F, 100.0F)));

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> function) {
        Block toRegister = function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(PleasureCraft.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(Registries.BLOCK, Identifier.of(PleasureCraft.MOD_ID, name), toRegister);
    }

    private static Block registerBlockWithoutBlockItem(String name, Function<AbstractBlock.Settings, Block> function) {
        return Registry.register(Registries.BLOCK, Identifier.of(PleasureCraft.MOD_ID, name),
                function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(PleasureCraft.MOD_ID, name)))));
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(PleasureCraft.MOD_ID, name),
                new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PleasureCraft.MOD_ID, name)))));
    }

    public static void registerBlocks() {
        PleasureCraft.LOGGER.info("Registering Block for PleasureCraft");
    }
}
