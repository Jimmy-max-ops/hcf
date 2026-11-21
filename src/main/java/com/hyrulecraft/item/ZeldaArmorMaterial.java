package com.hyrulecraft.item;

import com.hyrulecraft.HyruleCraft;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

public enum ZeldaArmorMaterial implements ArmorMaterial {
    // protection order: helmet, chestplate, leggings, boots
    HERO("hero", 26, new int[]{3, 7, 6, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0f, 0.0f,
            () -> Ingredient.ofItems(Items.EMERALD), "Strength I"),
    CHAMPION("champion", 30, new int[]{3, 8, 6, 3}, 12, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0f, 0.05f,
            () -> Ingredient.ofItems(Items.DIAMOND), "Resistance I"),
    ZORA("zora", 24, new int[]{2, 6, 5, 2}, 18, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, 0.0f, 0.0f,
            () -> Ingredient.ofItems(Items.PRISMARINE_CRYSTALS), "Water Breathing, Dolphin's Grace in water"),
    GORON("goron", 34, new int[]{3, 7, 6, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.1f,
            () -> Ingredient.ofItems(Items.OBSIDIAN), "Fire Resistance, Haste I");

    private static final int[] BASE_DURABILITY = {11, 16, 15, 13};

    private final String name;
    private final int durabilityMultiplier, enchantability;
    private final int[] protection;
    private final SoundEvent equipSound;
    private final float toughness, knockbackResistance;
    private final Supplier<Ingredient> repair;
    private final String bonus;

    ZeldaArmorMaterial(String name, int dm, int[] prot, int ench, SoundEvent sound, float tough, float kb,
                       Supplier<Ingredient> repair, String bonus) {
        this.name = name; this.durabilityMultiplier = dm; this.protection = prot; this.enchantability = ench;
        this.equipSound = sound; this.toughness = tough; this.knockbackResistance = kb;
        this.repair = repair; this.bonus = bonus;
    }

    public String getSetName() { return name; }
    public String getBonus() { return bonus; }

    @Override public int getDurability(ArmorItem.Type type) { return BASE_DURABILITY[type.ordinal()] * durabilityMultiplier; }
    @Override public int getProtection(ArmorItem.Type type) { return protection[type.ordinal()]; }
    @Override public int getEnchantability() { return enchantability; }
    @Override public SoundEvent getEquipSound() { return equipSound; }
    @Override public Ingredient getRepairIngredient() { return repair.get(); }
    @Override public String getName() { return HyruleCraft.MOD_ID + ":" + name; }
    @Override public float getToughness() { return toughness; }
    @Override public float getKnockbackResistance() { return knockbackResistance; }
}
