package com.hyrulecraft.item;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

/** Full-set bonuses, refreshed once a second. */
public final class ArmorEffects {
    private ArmorEffects() {}

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % 20 != 0) return;
            for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
                if (fullSet(p, ZeldaArmorMaterial.HERO)) apply(p, StatusEffects.STRENGTH);
                if (fullSet(p, ZeldaArmorMaterial.CHAMPION)) apply(p, StatusEffects.RESISTANCE);
                if (fullSet(p, ZeldaArmorMaterial.ZORA)) {
                    apply(p, StatusEffects.WATER_BREATHING);
                    if (p.isTouchingWater()) apply(p, StatusEffects.DOLPHINS_GRACE);
                }
                if (fullSet(p, ZeldaArmorMaterial.GORON)) {
                    apply(p, StatusEffects.FIRE_RESISTANCE);
                    apply(p, StatusEffects.HASTE);
                }
            }
        });
    }

    private static boolean fullSet(PlayerEntity p, ZeldaArmorMaterial mat) {
        for (ItemStack s : p.getArmorItems()) {
            if (!(s.getItem() instanceof ArmorItem a) || a.getMaterial() != mat) return false;
        }
        return true;
    }

    private static void apply(PlayerEntity p, StatusEffect effect) {
        p.addStatusEffect(new StatusEffectInstance(effect, 80, 0, true, false, true));
    }
}
