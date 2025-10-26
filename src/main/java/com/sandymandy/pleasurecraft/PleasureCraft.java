package com.sandymandy.pleasurecraft;

import com.sandymandy.pleasurecraft.advancement.criterion.PleasureCraftCriteria;
import com.sandymandy.pleasurecraft.block.PleasureCraftBlocks;
import com.sandymandy.pleasurecraft.block.entity.PleasureCraftBlockEntities;
import com.sandymandy.pleasurecraft.entity.ai.brain.GirlMemoryTypes;
import com.sandymandy.pleasurecraft.item.PleasureCraftItemGroups;
import com.sandymandy.pleasurecraft.item.PleasureCraftItems;
import com.sandymandy.pleasurecraft.networking.PleasureCraftPackets;
import com.sandymandy.pleasurecraft.registries.GirlRegistry;
import com.sandymandy.pleasurecraft.registries.PleasureCraftScreenHandlerRegistry;
import com.sandymandy.pleasurecraft.registries.PleasureCraftSoundEventRegistry;
import com.sandymandy.pleasurecraft.registries.PleasureCraftTrackedDataRegistry;
import com.sandymandy.pleasurecraft.settlement.SettlementManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PleasureCraft implements ModInitializer {
	public static final String MOD_ID = "pleasurecraft";
	public static final Logger LOGGER = LoggerFactory.getLogger("PleasureCraft");

	@Override
	public void onInitialize() {
		ServerTickEvents.END_WORLD_TICK.register(world -> {
			if (!world.isClient()) {
				SettlementManager.get(world).tick(world);
			}
		});

		PleasureCraftPackets.registerPackets();
		PleasureCraftPackets.registerC2SPackets();
		PleasureCraftItemGroups.registerItemGroups();
		PleasureCraftItems.registerItems();
		PleasureCraftBlockEntities.registerBlockEntities();
		PleasureCraftBlocks.registerBlocks();
		PleasureCraftCriteria.registerAdvancementCriteria();
		PleasureCraftTrackedDataRegistry.registerTrackedData();
		PleasureCraftSoundEventRegistry.registerSoundEvents();
		PleasureCraftScreenHandlerRegistry.register();
		GirlMemoryTypes.registerMemoryTypes();
		GirlRegistry.register();
	}
}