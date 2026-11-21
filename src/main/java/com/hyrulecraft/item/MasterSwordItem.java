package com.hyrulecraft.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** The blade that seals the darkness: smites undead, and fires a sword beam at full health. */
public class MasterSwordItem extends SwordItem {
    public MasterSwordItem(Settings settings) {
        super(ZeldaToolMaterial.MASTER, 3, -2.0f, settings);
    }

    @Override public boolean hasGlint(ItemStack stack) { return true; }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient && target.getGroup() == EntityGroup.UNDEAD) {
            target.timeUntilRegen = 0;
            target.damage(attacker.getDamageSources().magic(), 6.0f);
            target.setOnFireFor(4);
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.getHealth() < player.getMaxHealth() - 0.01f) return TypedActionResult.pass(stack);
        if (!world.isClient) {
            BeamUtil.fire(world, player, 24, 8.0f, ParticleTypes.END_ROD);
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1.0f, 1.5f);
            stack.damage(3, player, p -> p.sendToolBreakStatus(hand));
        }
        player.getItemCooldownManager().set(this, 30);
        player.swingHand(hand);
        return TypedActionResult.success(stack, world.isClient);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext ctx) {
        tooltip.add(Text.literal("The Blade of Evil's Bane").formatted(Formatting.AQUA));
        tooltip.add(Text.literal("Smites the undead.").formatted(Formatting.GRAY));
        tooltip.add(Text.literal("Right-click at full health: Sword Beam").formatted(Formatting.GRAY));
    }
}
