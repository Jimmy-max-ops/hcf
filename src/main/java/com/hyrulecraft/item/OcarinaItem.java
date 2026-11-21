package com.hyrulecraft.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

/** Sneak + right-click picks a song, right-click plays it. */
public class OcarinaItem extends Item {
    private static final String[] SONGS = {"Sun's Song", "Song of Storms", "Epona's Song", "Zelda's Lullaby"};
    private static final String[] DESC = {"Skips to the next noon", "Toggles rain and thunder",
            "Summons a saddled horse", "Regeneration for nearby players"};

    public OcarinaItem(Settings settings) { super(settings); }

    private static int song(ItemStack s) { return Math.floorMod(s.getOrCreateNbt().getInt("Song"), SONGS.length); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.isSneaking()) {
            int next = (song(stack) + 1) % SONGS.length;
            stack.getOrCreateNbt().putInt("Song", next);
            if (!world.isClient)
                player.sendMessage(Text.literal("\u266A " + SONGS[next] + " - " + DESC[next]).formatted(Formatting.AQUA), true);
            return TypedActionResult.success(stack, world.isClient);
        }
        if (world.isClient) return TypedActionResult.success(stack, true);

        ServerWorld sw = (ServerWorld) world;
        int s = song(stack);
        int cooldown = 40;
        switch (s) {
            case 0 -> {
                long t = sw.getTimeOfDay();
                long day = t / 24000L;
                sw.setTimeOfDay((t % 24000L) < 6000L ? day * 24000L + 6000L : (day + 1) * 24000L + 6000L);
            }
            case 1 -> {
                if (sw.isRaining()) sw.setWeather(12000, 0, false, false);
                else sw.setWeather(0, 6000, true, true);
            }
            case 2 -> {
                HorseEntity horse = EntityType.HORSE.create(sw);
                if (horse != null) {
                    horse.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), player.getYaw(), 0);
                    horse.initialize(sw, sw.getLocalDifficulty(player.getBlockPos()), SpawnReason.MOB_SUMMONED, null, null);
                    horse.setTame(true);
                    horse.setOwnerUuid(player.getUuid());
                    horse.saddle(SoundCategory.PLAYERS);
                    horse.setCustomName(Text.literal("Epona"));
                    sw.spawnEntity(horse);
                }
                cooldown = 600;
            }
            case 3 -> {
                for (PlayerEntity p : sw.getEntitiesByClass(PlayerEntity.class, player.getBoundingBox().expand(8), x -> true))
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 1));
                cooldown = 100;
            }
            default -> {}
        }
        // a little chord
        float[] pitches = {1.0f, 1.26f, 1.5f};
        for (float pitch : pitches)
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 1.2f, pitch);
        player.sendMessage(Text.literal("\u266A " + SONGS[s]).formatted(Formatting.AQUA), true);
        player.getItemCooldownManager().set(this, cooldown);
        return TypedActionResult.success(stack, false);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext ctx) {
        tooltip.add(Text.literal("Song: " + SONGS[song(stack)]).formatted(Formatting.AQUA));
        tooltip.add(Text.literal("Sneak + right-click: change song").formatted(Formatting.GRAY));
    }
}
