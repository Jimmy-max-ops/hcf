package com.hyrulecraft.client;

import com.hyrulecraft.HyruleCraft;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;

public class HyruleBipedRenderer<T extends HostileEntity> extends BipedEntityRenderer<T, HyruleBipedModel<T>> {
    private final Identifier texture;
    private final float scale;

    public HyruleBipedRenderer(EntityRendererFactory.Context ctx, String name, float scale) {
        super(ctx, new HyruleBipedModel<>(ctx.getPart(EntityModelLayers.ZOMBIE)), 0.5f * scale);
        this.texture = HyruleCraft.id("textures/entity/" + name + ".png");
        this.scale = scale;
    }

    @Override public Identifier getTexture(T entity) { return texture; }

    @Override
    protected void scale(T entity, MatrixStack matrices, float amount) {
        matrices.scale(scale, scale, scale);
    }
}
