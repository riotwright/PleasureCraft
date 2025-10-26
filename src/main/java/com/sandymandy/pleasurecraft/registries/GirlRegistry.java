package com.sandymandy.pleasurecraft.registries;

import com.sandymandy.pleasurecraft.PleasureCraft;
import com.sandymandy.pleasurecraft.entity.PleasureCraftEntities;
import com.sandymandy.pleasurecraft.entity.girls.MikaEntity;
import com.sandymandy.pleasurecraft.entity.girls.LucyEntity;
import com.sandymandy.pleasurecraft.entity.girls.MomoEntity;
import net.minecraft.entity.EntityType;

public class GirlRegistry {
    public static final EntityType<LucyEntity> LUCY = PleasureCraftEntities.registerGirl("lucy", LucyEntity::new, 0.5f, 1.95f, LucyEntity::createAttributes);

    public static final EntityType<MikaEntity> MIKA = PleasureCraftEntities.registerGirl("mika", MikaEntity::new, 0.5f, 1.95f, MikaEntity::createAttributes);

    public static final EntityType<MomoEntity> MOMO = PleasureCraftEntities.registerGirl("momo", MomoEntity::new, 0.5f, 1.65f, MomoEntity::createAttributes);

    public static void register() {
        PleasureCraft.LOGGER.info("Registering Girls for PleasureCraft");
        // Trigger attribute registration
        PleasureCraftEntities.registerAttributes();
    }
}
