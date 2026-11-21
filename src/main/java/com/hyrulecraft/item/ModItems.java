package com.hyrulecraft.item;

import com.hyrulecraft.HyruleCraft;
import com.hyrulecraft.entity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ENTRIES = new ArrayList<>();
    public static final List<Item> ARMOR = new ArrayList<>();

    private static <T extends Item> T reg(String name, T item) {
        Registry.register(Registries.ITEM, HyruleCraft.id(name), item);
        ENTRIES.add(item);
        return item;
    }

    private static FabricItemSettings s() { return new FabricItemSettings(); }

    // ---- weapons & shield ----
    public static final Item MASTER_SWORD = reg("master_sword", new MasterSwordItem(s().rarity(Rarity.EPIC).fireproof()));
    public static final Item BIGGORON_SWORD = reg("biggoron_sword", new BiggoronSwordItem(s().rarity(Rarity.RARE)));
    public static final Item BOKO_CLUB = reg("boko_club", new BokoClubItem(s()));
    public static final Item HYLIAN_SHIELD = reg("hylian_shield", new HylianShieldItem(s().maxDamage(1200).rarity(Rarity.RARE)));

    // ---- iconic gear ----
    public static final Item HOOKSHOT = reg("hookshot", new HookshotItem(s().maxDamage(250)));
    public static final Item OCARINA_OF_TIME = reg("ocarina_of_time", new OcarinaItem(s().maxCount(1).rarity(Rarity.RARE)));
    public static final Item SHEIKAH_SLATE = reg("sheikah_slate", new SheikahSlateItem(s().maxCount(1).rarity(Rarity.RARE)));
    public static final Item KOROK_LEAF = reg("korok_leaf", new KorokLeafItem(s().maxDamage(200)));
    public static final Item HEART_CONTAINER = reg("heart_container", new HeartContainerItem(s().maxCount(16).rarity(Rarity.EPIC)));
    public static final Item FAIRY_BOTTLE = reg("fairy_bottle", new FairyBottleItem(s().maxCount(4).rarity(Rarity.UNCOMMON)));

    // ---- currency & mob drops ----
    public static final Item RUPEE_GREEN = reg("rupee_green", new Item(s()));
    public static final Item RUPEE_BLUE = reg("rupee_blue", new Item(s()));
    public static final Item RUPEE_RED = reg("rupee_red", new Item(s().rarity(Rarity.UNCOMMON)));
    public static final Item RUPEE_PURPLE = reg("rupee_purple", new Item(s().rarity(Rarity.RARE)));
    public static final Item RUPEE_GOLD = reg("rupee_gold", new Item(s().rarity(Rarity.EPIC)));
    public static final Item BOKOBLIN_HORN = reg("bokoblin_horn", new Item(s()));
    public static final Item MOBLIN_HORN = reg("moblin_horn", new Item(s()));
    public static final Item LIZALFOS_HORN = reg("lizalfos_horn", new Item(s()));
    public static final Item CHUCHU_JELLY = reg("chuchu_jelly", new Item(s().food(
            new FoodComponent.Builder().hunger(3).saturationModifier(0.4f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 400), 1.0f).build())));

    // ---- spawn eggs ----
    public static final Item BOKOBLIN_SPAWN_EGG = reg("bokoblin_spawn_egg", new SpawnEggItem(ModEntities.BOKOBLIN, 0xC4483A, 0x6B3B1E, s()));
    public static final Item MOBLIN_SPAWN_EGG = reg("moblin_spawn_egg", new SpawnEggItem(ModEntities.MOBLIN, 0x8E3B8E, 0x3C2A1A, s()));
    public static final Item LIZALFOS_SPAWN_EGG = reg("lizalfos_spawn_egg", new SpawnEggItem(ModEntities.LIZALFOS, 0x4F9A4B, 0xD8C36A, s()));
    public static final Item CHUCHU_SPAWN_EGG = reg("chuchu_spawn_egg", new SpawnEggItem(ModEntities.CHUCHU, 0x4A8FE0, 0xBFE3FF, s()));

    // ---- armor sets: hero, champion, zora, goron (helmet/chestplate/leggings/boots each) ----
    static {
        for (ZeldaArmorMaterial m : ZeldaArmorMaterial.values()) {
            for (ArmorItem.Type t : ArmorItem.Type.values()) {
                ARMOR.add(reg(m.getSetName() + "_" + t.getName(), new ZeldaArmorItem(m, t, s())));
            }
        }
    }

    public static final ItemGroup GROUP = Registry.register(Registries.ITEM_GROUP, HyruleCraft.id("hyrule"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.hyrulecraft.hyrule"))
                    .icon(() -> new ItemStack(MASTER_SWORD))
                    .entries((ctx, entries) -> ENTRIES.forEach(i -> entries.add(i)))
                    .build());

    public static void init() {}
}
