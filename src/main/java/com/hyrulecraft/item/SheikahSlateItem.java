package com.hyrulecraft.item;

import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** Right-click: Stasis on the mob you look at. Sneak + right-click on water: Cryonis ice pillar. */
public class SheikahSlateItem extends Item {
    private static final double RANGE = 32.0;

    public SheikahSlateItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        Vec3d start = player.getEyePos();
        Vec3d look = player.getRotationVec(1.0f);
        Vec3d end = start.add(look.multiply(RANGE));

        if (player.isSneaking()) {
            BlockHitResult bhr = world.raycast(new RaycastContext(start, end,
                    RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.SOURCE_ONLY, player));
            if (bhr.getType() == HitResult.Type.MISS || !world.getFluidState(bhr.getBlockPos()).isIn(FluidTags.WATER))
                return TypedActionResult.fail(stack);
            if (!world.isClient) {
                BlockPos base = bhr.getBlockPos();
                for (int i = 0; i < 3; i++) {
                    BlockPos p = base.up(i);
                    if (world.getBlockState(p).isOf(Blocks.WATER) || world.isAir(p))
                        world.setBlockState(p, Blocks.ICE.getDefaultState());
                    else break;
                }
                world.playSound(null, base, SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.PLAYERS, 1.0f, 0.8f);
            }
            player.getItemCooldownManager().set(this, 40);
            return TypedActionResult.success(stack, world.isClient);
        }

        Box box = player.getBoundingBox().stretch(look.multiply(RANGE)).expand(1.0);
        EntityHitResult ehr = ProjectileUtil.raycast(player, start, end, box,
                e -> e instanceof LivingEntity && !e.isSpectator(), RANGE * RANGE);
        if (ehr == null) return TypedActionResult.fail(stack);

        if (!world.isClient) {
            LivingEntity t = (LivingEntity) ehr.getEntity();
            t.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 6));
            t.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 160, 4));
            t.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 160));
            t.setVelocity(Vec3d.ZERO);
            t.velocityModified = true;
            world.playSound(null, t.getX(), t.getY(), t.getZ(), SoundEvents.BLOCK_BEACON_DEACTIVATE,
                    SoundCategory.PLAYERS, 1.0f, 1.6f);
            player.sendMessage(Text.literal("Stasis!").formatted(Formatting.GOLD), true);
        }
        player.getItemCooldownManager().set(this, 300);
        return TypedActionResult.success(stack, world.isClient);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext ctx) {
        tooltip.add(Text.literal("Right-click: Stasis (freeze a mob)").formatted(Formatting.GRAY));
        tooltip.add(Text.literal("Sneak + right-click water: Cryonis").formatted(Formatting.GRAY));
    }
}
