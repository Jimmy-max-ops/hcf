package com.hyrulecraft.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/** Shared melee-monster behaviour for Bokoblin, Moblin and Lizalfos. */
public abstract class HyruleMonsterEntity extends HostileEntity {
    protected HyruleMonsterEntity(EntityType<? extends HyruleMonsterEntity> type, World world) {
        super(type, world);
    }

    protected abstract ItemStack createWeapon();

    @Override
    protected void initGoals() {
        goalSelector.add(1, new SwimGoal(this));
        goalSelector.add(2, new MeleeAttackGoal(this, 1.15, false));
        goalSelector.add(5, new WanderAroundFarGoal(this, 0.9));
        goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        goalSelector.add(7, new LookAroundGoal(this));
        targetSelector.add(1, new RevengeGoal(this));
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason reason,
                                 @Nullable EntityData data, @Nullable NbtCompound nbt) {
        data = super.initialize(world, difficulty, reason, data, nbt);
        equipStack(EquipmentSlot.MAINHAND, createWeapon());
        setEquipmentDropChance(EquipmentSlot.MAINHAND, 0.06f);
        return data;
    }

    @Override protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_PIGLIN_BRUTE_AMBIENT; }
    @Override protected SoundEvent getHurtSound(DamageSource source) { return SoundEvents.ENTITY_PIGLIN_BRUTE_HURT; }
    @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_PIGLIN_BRUTE_DEATH; }
}
