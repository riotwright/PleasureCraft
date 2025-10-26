package com.sandymandy.pleasurecraft.advancement.criterion;

import com.mojang.serialization.Codec;
import com.sandymandy.pleasurecraft.PleasureCraft;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static net.minecraft.advancement.criterion.Criteria.IMPOSSIBLE;

public class PleasureCraftCriteria {
    public static final Codec<Criterion<?>> CODEC = Registries.CRITERION.getCodec();
    public static final TameGirlCriterion TAME_GIRL = register("tame_girl", new TameGirlCriterion());


    public static <T extends Criterion<?>> T register(String id, T criterion) {
        return Registry.register(Registries.CRITERION, id, criterion);
    }

    public static Criterion<?> getDefault(Registry<Criterion<?>> registry) {
        return IMPOSSIBLE;
    }

    public static void registerAdvancementCriteria() {
        PleasureCraft.LOGGER.info("Registering Advancement Criteria for PleasureCraft");
    }
}
