package com.hyrulecraft.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;

public class HylianShieldItem extends ShieldItem {
    public HylianShieldItem(Settings settings) { super(settings); }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.IRON_INGOT);
    }
}
