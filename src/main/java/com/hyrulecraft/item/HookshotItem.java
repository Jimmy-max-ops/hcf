package com.hyrulecraft.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** Zip to blocks, or yank enemies toward you. */
public class HookshotItem extends Item {
    private static final double RANGE = 28.0;

    public HookshotItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        Vec3d start = player.getEyePos();
        Vec3d look = player.getRotationVec(1.0f);
        Vec3d end = start.add(look.multiply(RANGE));

        BlockHitResult bhr = world.raycast(new RaycastContext(start, end,
                RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));
        Box box = player.getBoundingBox().stretch(look.multiply(RANGE)).expand(1.0);
        EntityHitResult ehr = ProjectileUtil.raycast(player, start, end, box,
                e -> !e.isSpectator() && e.isAttackable(), RANGE * RANGE);

        double blockDist = bhr.getType() == HitResult.Type.MISS ? Double.MAX_VALUE : start.distanceTo(bhr.getPos());
        double entDist = ehr == null ? Double.MAX_VALUE : start.distanceTo(ehr.getPos());
        if (blockDist == Double.MAX_VALUE && entDist == Double.MAX_VALUE) return TypedActionResult.fail(stack);

        if (!world.isClient) {
            Vec3d target;
            if (entDist < blockDist) {
                Entity e = ehr.getEntity();
                target = ehr.getPos();
                Vec3d pull = player.getPos().add(0, 1, 0).subtract(e.getPos()).normalize().multiply(1.6);
                e.setVelocity(pull.x, pull.y + 0.3, pull.z);
                e.velocityModified = true;
            } else {
                target = bhr.getPos();
                Vec3d pull = target.subtract(start).normalize().multiply(2.0);
                player.setVelocity(pull.x, pull.y + 0.2, pull.z);
                player.velocityModified = true;
                player.fallDistance = 0;
            }
            ServerWorld sw = (ServerWorld) world;
            double len = start.distanceTo(target);
            for (double d = 0; d < len; d += 0.6) {
                Vec3d p = start.add(look.multiply(d));
                sw.spawnParticles(ParticleTypes.CRIT, p.x, p.y - 0.2, p.z, 1, 0, 0, 0, 0);
            }
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BLOCK_CHAIN_PLACE, SoundCategory.PLAYERS, 1.0f, 1.2f);
            stack.damage(1, player, p -> p.sendToolBreakStatus(hand));
        }
        player.getItemCooldownManager().set(this, 30);
        player.swingHand(hand);
        return TypedActionResult.success(stack, world.isClient);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext ctx) {
        tooltip.add(Text.literal("Right-click: pull yourself to a block, or pull a mob to you").formatted(Formatting.GRAY));
    }
}
