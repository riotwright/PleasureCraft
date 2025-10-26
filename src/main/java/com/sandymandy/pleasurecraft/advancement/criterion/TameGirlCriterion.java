package com.sandymandy.pleasurecraft.advancement.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sandymandy.pleasurecraft.PleasureCraft;
import com.sandymandy.pleasurecraft.entity.base.TameableGirlEntity;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.entity.LootContextPredicateValidator;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class TameGirlCriterion extends AbstractCriterion<TameGirlCriterion.Conditions> {
    public static final Identifier ID = Identifier.of(PleasureCraft.MOD_ID, "tame_girl");

    @Override
    public Codec<TameGirlCriterion.Conditions> getConditionsCodec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayerEntity player, TameableGirlEntity entity) {
        LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, entity);
        this.trigger(player, conditions -> conditions.matches(lootContext));
    }

    public record Conditions(Optional<LootContextPredicate> player, Optional<LootContextPredicate> entity) implements AbstractCriterion.Conditions {
        public static final Codec<TameGirlCriterion.Conditions> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(Conditions::player),
                        EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("entity").forGetter(Conditions::entity)
                        )
                        .apply(instance, Conditions::new)
        );

        public static AdvancementCriterion<TameGirlCriterion.Conditions> any() {
            return PleasureCraftCriteria.TAME_GIRL.create(new TameGirlCriterion.Conditions(Optional.empty(), Optional.empty()));
        }

        public static AdvancementCriterion<TameGirlCriterion.Conditions> create(EntityPredicate.Builder entity) {
            return PleasureCraftCriteria.TAME_GIRL
                    .create(new TameGirlCriterion.Conditions(Optional.empty(), Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(entity))));
        }

        public boolean matches(LootContext entityCtx) {
            return this.entity.isEmpty() || this.entity.get().test(entityCtx);
        }

        @Override
        public void validate(LootContextPredicateValidator validator) {
            AbstractCriterion.Conditions.super.validate(validator);
            validator.validateEntityPredicate(this.entity, ".entity");
        }
    }
}

