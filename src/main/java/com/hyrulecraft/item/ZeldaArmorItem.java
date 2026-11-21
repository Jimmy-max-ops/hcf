package com.hyrulecraft.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZeldaArmorItem extends ArmorItem {
    private final ZeldaArmorMaterial zeldaMaterial;

    public ZeldaArmorItem(ZeldaArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
        this.zeldaMaterial = material;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Full set: " + zeldaMaterial.getBonus()).formatted(Formatting.GOLD));
    }
}
