package com.hyrulecraft.client;

import com.hyrulecraft.HyruleCraft;
import com.hyrulecraft.entity.ChuchuEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ChuchuRenderer extends MobEntityRenderer<ChuchuEntity, SlimeEntityModel<ChuchuEntity>> {
    private static final Identifier TEXTURE = HyruleCraft.id("textures/entity/chuchu.png");

    public ChuchuRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SlimeEntityModel<>(ctx.getPart(EntityModelLayers.SLIME)), 0.25f);
    }

    @Override
    public void render(ChuchuEntity e, float yaw, float tickDelta, MatrixStack m, VertexConsumerProvider v, int light) {
        this.shadowRadius = 0.25f * e.getSize();
        super.render(e, yaw, tickDelta, m, v, light);
    }

    @Override
    protected void scale(ChuchuEntity e, MatrixStack m, float tickDelta) {
        m.scale(0.999f, 0.999f, 0.999f);
        m.translate(0.0f, 0.001f, 0.0f);
        float size = e.getSize();
        float g = MathHelper.lerp(tickDelta, e.lastStretch, e.stretch) / (size * 0.5f + 1.0f);
        float h = 1.0f / (g + 1.0f);
        m.scale(h * size, 1.0f / h * size, h * size);
    }

    @Override public Identifier getTexture(ChuchuEntity entity) { return TEXTURE; }
}
