package com.hyrulecraft.client;

import com.hyrulecraft.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class HyruleCraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.BOKOBLIN, ctx -> new HyruleBipedRenderer<>(ctx, "bokoblin", 1.0f));
        EntityRendererRegistry.register(ModEntities.MOBLIN, ctx -> new HyruleBipedRenderer<>(ctx, "moblin", 1.35f));
        EntityRendererRegistry.register(ModEntities.LIZALFOS, ctx -> new HyruleBipedRenderer<>(ctx, "lizalfos", 1.0f));
        EntityRendererRegistry.register(ModEntities.CHUCHU, ChuchuRenderer::new);
    }
}
