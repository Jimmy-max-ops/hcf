package com.hyrulecraft.entity;

import com.hyrulecraft.HyruleCraft;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntities {
    private static <T extends Entity> EntityType<T> reg(String name, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, HyruleCraft.id(name), type);
    }

    public static final EntityType<BokoblinEntity> BOKOBLIN = reg("bokoblin",
            FabricEntityTypeBuilder.<BokoblinEntity>createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .entityFactory(BokoblinEntity::new)
                    .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                    .defaultAttributes(BokoblinEntity::createAttributes)
                    .build());

    public static final EntityType<MoblinEntity> MOBLIN = reg("moblin",
            FabricEntityTypeBuilder.<MoblinEntity>createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .entityFactory(MoblinEntity::new)
                    .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark)
                    .dimensions(EntityDimensions.fixed(0.9f, 2.7f))
                    .defaultAttributes(MoblinEntity::createAttributes)
                    .build());

    public static final EntityType<LizalfosEntity> LIZALFOS = reg("lizalfos",
            FabricEntityTypeBuilder.<LizalfosEntity>createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .entityFactory(LizalfosEntity::new)
                    .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                    .defaultAttributes(LizalfosEntity::createAttributes)
                    .build());

    public static final EntityType<ChuchuEntity> CHUCHU = reg("chuchu",
            FabricEntityTypeBuilder.<ChuchuEntity>createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .entityFactory(ChuchuEntity::new)
                    .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                            (t, w, r, pos, rand) -> w.getDifficulty() != Difficulty.PEACEFUL && MobEntity.canMobSpawn(t, w, r, pos, rand))
                    .dimensions(EntityDimensions.fixed(2.04f, 2.04f))
                    .trackRangeBlocks(10)
                    .defaultAttributes(ChuchuEntity::createAttributes)
                    .build());

    public static void init() {
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, BOKOBLIN, 40, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, MOBLIN, 10, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, LIZALFOS, 15, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SWAMP, BiomeKeys.FOREST),
                SpawnGroup.MONSTER, CHUCHU, 20, 1, 2);
    }
}
