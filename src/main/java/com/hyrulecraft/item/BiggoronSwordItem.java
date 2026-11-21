package com.hyrulecraft.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** Huge, slow, devastating. Right-click for a Spin Attack. */
public class BiggoronSwordItem extends SwordItem {
    public BiggoronSwordItem(Settings settings) {
        super(ZeldaToolMaterial.BIGGORON, 3, -3.3f, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient) {
            ServerWorld sw = (ServerWorld) world;
            for (Entity e : world.getOtherEntities(player, player.getBoundingBox().expand(3.5, 0.5, 3.5),
                    en -> en instanceof LivingEntity && en.isAlive())) {
                e.damage(player.getDamageSources().playerAttack(player), 8.0f);
                ((LivingEntity) e).takeKnockback(1.2, player.getX() - e.getX(), player.getZ() - e.getZ());
            }
            for (int i = 0; i < 16; i++) {
                double a = i * Math.PI / 8.0;
                sw.spawnParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + Math.cos(a) * 2.5,
                        player.getBodyY(0.5), player.getZ() + Math.sin(a) * 2.5, 1, 0, 0, 0, 0);
            }
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1.2f, 0.7f);
            stack.damage(4, player, p -> p.sendToolBreakStatus(hand));
        }
        player.getItemCooldownManager().set(this, 50);
        player.swingHand(hand);
        return TypedActionResult.success(stack, world.isClient);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext ctx) {
        tooltip.add(Text.literal("Right-click: Spin Attack").formatted(Formatting.GRAY));
    }
}
