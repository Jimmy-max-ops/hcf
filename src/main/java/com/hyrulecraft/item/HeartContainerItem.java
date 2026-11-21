package com.hyrulecraft.item;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/** +1 permanent heart (max 20 hearts). Persists through death. */
public class HeartContainerItem extends Item {
    private static final double MAX_HEALTH_CAP = 40.0;

    public HeartContainerItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        EntityAttributeInstance attr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (attr == null || attr.getBaseValue() >= MAX_HEALTH_CAP) {
            if (!world.isClient) player.sendMessage(Text.literal("You can't hold any more hearts!").formatted(Formatting.RED), true);
            return TypedActionResult.fail(stack);
        }
        if (!world.isClient) {
            attr.setBaseValue(attr.getBaseValue() + 2.0);
            player.heal((float) attr.getValue());
            if (!player.getAbilities().creativeMode) stack.decrement(1);
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        return TypedActionResult.success(stack, world.isClient);
    }
}
