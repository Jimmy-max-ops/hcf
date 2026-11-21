package com.hyrulecraft;

import com.hyrulecraft.entity.ModEntities;
import com.hyrulecraft.item.ArmorEffects;
import com.hyrulecraft.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;

public class HyruleCraft implements ModInitializer {
    public static final String MOD_ID = "hyrulecraft";

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModEntities.init();
        ModItems.init();
        ArmorEffects.init();

        // Heart Containers survive death.
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            EntityAttributeInstance o = oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            EntityAttributeInstance n = newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (o != null && n != null) n.setBaseValue(o.getBaseValue());
        });
    }
}
