package com.hyrulecraft.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/** Release the fairy: full heal, regeneration, absorption. Returns an empty bottle. */
public class FairyBottleItem extends Item {
    public FairyBottleItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient) {
            player.heal(player.getMaxHealth());
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 1));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 600, 1));
            ((ServerWorld) world).spawnParticles(ParticleTypes.HEART, player.getX(), player.getBodyY(0.8), player.getZ(), 12, 0.5, 0.5, 0.5, 0.05);
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 1.5f, 1.6f);
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
                ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
                if (!player.getInventory().insertStack(bottle)) player.dropItem(bottle, false);
            }
        }
        return TypedActionResult.success(stack, world.isClient);
    }
}
