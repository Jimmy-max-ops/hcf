package com.hyrulecraft.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public final class BeamUtil {
    private BeamUtil() {}

    /** Straight beam that damages every living entity it passes through and stops at solid blocks. */
    public static void fire(World world, PlayerEntity player, double range, float damage, ParticleEffect particle) {
        if (!(world instanceof ServerWorld sw)) return;
        Vec3d start = player.getEyePos();
        Vec3d look = player.getRotationVec(1.0f);
        Set<Entity> hit = new HashSet<>();
        for (double d = 1.0; d <= range; d += 0.5) {
            Vec3d p = start.add(look.multiply(d));
            BlockPos bp = BlockPos.ofFloored(p);
            if (!world.getBlockState(bp).getCollisionShape(world, bp).isEmpty()) break;
            sw.spawnParticles(particle, p.x, p.y, p.z, 2, 0.05, 0.05, 0.05, 0.0);
            for (Entity e : world.getOtherEntities(player, new Box(p, p).expand(0.7),
                    en -> en instanceof LivingEntity && en.isAlive())) {
                if (hit.add(e)) e.damage(player.getDamageSources().playerAttack(player), damage);
            }
        }
    }
}
