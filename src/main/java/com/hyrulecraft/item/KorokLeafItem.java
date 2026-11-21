package com.hyrulecraft.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** Wind gust: blasts mobs away, cushions your fall, and gives you a boost in mid-air. */
public class KorokLeafItem extends Item {
    public KorokLeafItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        Vec3d look = player.getRotationVec(1.0f);
        if (!world.isClient) {
            ServerWorld sw = (ServerWorld) world;
            for (Entity e : world.getOtherEntities(player, player.getBoundingBox().expand(7), en -> en instanceof LivingEntity)) {
                Vec3d to = e.getPos().subtract(player.getPos()).normalize();
                if (to.dotProduct(look) > 0.45) {
                    e.addVelocity(look.x * 1.4, 0.45, look.z * 1.4);
                    e.velocityModified = true;
                }
            }
            for (int i = 1; i <= 6; i++) {
                Vec3d p = player.getEyePos().add(look.multiply(i));
                sw.spawnParticles(ParticleTypes.CLOUD, p.x, p.y, p.z, 4, 0.3, 0.3, 0.3, 0.02);
            }
            if (!player.isOnGround()) {
                player.addVelocity(look.x * 0.5, 0.35, look.z * 0.5);
                player.velocityModified = true;
            }
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false, true));
            player.fallDistance = 0;
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 1.0f, 1.3f);
            stack.damage(1, player, p -> p.sendToolBreakStatus(hand));
        }
        player.getItemCooldownManager().set(this, 25);
        player.swingHand(hand);
        return TypedActionResult.success(stack, world.isClient);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext ctx) {
        tooltip.add(Text.literal("Right-click: wind gust").formatted(Formatting.GRAY));
    }
}
