package com.hyrulecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

/** Cheap, fragile, and sends enemies flying. */
public class BokoClubItem extends SwordItem {
    public BokoClubItem(Settings settings) {
        super(ZeldaToolMaterial.BOKO, 3, -2.8f, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.takeKnockback(1.0, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        return super.postHit(stack, target, attacker);
    }
}
