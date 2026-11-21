package com.hyrulecraft.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;

/** A slime that splits like one, and leaves you sluggish on contact. */
public class ChuchuEntity extends SlimeEntity {
    public ChuchuEntity(EntityType<? extends SlimeEntity> type, World world) { super(type, world); }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes();
    }

    @Override
    protected void damage(LivingEntity target) {
        super.damage(target);
        if (isAlive()) target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0));
    }
}
