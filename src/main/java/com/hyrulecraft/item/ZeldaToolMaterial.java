package com.hyrulecraft.item;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;

import java.util.function.Supplier;

public enum ZeldaToolMaterial implements ToolMaterial {
    MASTER(4, 2500, 9.0f, 5.0f, 22, () -> Ingredient.ofItems(Items.DIAMOND)),
    BIGGORON(3, 1800, 4.0f, 7.0f, 10, () -> Ingredient.ofItems(Items.NETHERITE_SCRAP)),
    BOKO(0, 60, 2.0f, 1.0f, 8, () -> Ingredient.fromTag(ItemTags.LOGS));

    private final int level, durability, enchantability;
    private final float speed, damage;
    private final Supplier<Ingredient> repair;

    ZeldaToolMaterial(int level, int durability, float speed, float damage, int ench, Supplier<Ingredient> repair) {
        this.level = level; this.durability = durability; this.speed = speed;
        this.damage = damage; this.enchantability = ench; this.repair = repair;
    }

    @Override public int getDurability() { return durability; }
    @Override public float getMiningSpeedMultiplier() { return speed; }
    @Override public float getAttackDamage() { return damage; }
    @Override public int getMiningLevel() { return level; }
    @Override public int getEnchantability() { return enchantability; }
    @Override public Ingredient getRepairIngredient() { return repair.get(); }
}
