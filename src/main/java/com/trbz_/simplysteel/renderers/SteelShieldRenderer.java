package com.trbz_.simplysteel.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.trbz_.simplysteel.SimplySteel;
import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = SimplySteel.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SteelShieldRenderer extends BlockEntityWithoutLevelRenderer {
    private ShieldModel shieldModel;
    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    public static SteelShieldRenderer instance;
    private final EntityModelSet entityModelSet;
    public static ModelLayerLocation STEEL_SHIELD = new ModelLayerLocation(new ResourceLocation(SimplySteel.MOD_ID, "steel_shield"), "main");
    public static final Material STEEL_SHIELD_BASE = new Material(new ResourceLocation(SimplySteel.MOD_ID, "textures/atlas/steel_shield_patterns.png"), new ResourceLocation(SimplySteel.MOD_ID, "entity/steel_shield_base"));
    public static final Material STEEL_NO_PATTERN_SHIELD = new Material(new ResourceLocation(SimplySteel.MOD_ID, "textures/atlas/steel_shield_patterns.png"), new ResourceLocation(SimplySteel.MOD_ID, "entity/steel_shield_base_nopattern"));
//    public static final Material STEEL_SHIELD_BASE = new Material(Sheets.SHIELD_SHEET, new ResourceLocation(SimplySteel.MOD_ID + ":textures/entity/shield_base.png"));
//    public static final Material STEEL_NO_PATTERN_SHIELD = new Material(Sheets.SHIELD_SHEET, new ResourceLocation(SimplySteel.MOD_ID + ":textures/entity/shield_base_nopattern.png"));
//    public static final Material STEEL_SHIELD_BASE = new Material(Sheets.SHIELD_SHEET, new ResourceLocation("entity/shield_base"));
//    public static final Material STEEL_NO_PATTERN_SHIELD = new Material(Sheets.SHIELD_SHEET, new ResourceLocation("entity/shield_base_nopattern"));
//    public static final Material STEEL_SHIELD_BASE = new Material(new ResourceLocation("textures/atlas/shield_patterns.png"), new ResourceLocation("entity/shield_base"));
//    public static final Material STEEL_NO_PATTERN_SHIELD = new Material(new ResourceLocation("textures/atlas/shield_patterns.png"), new ResourceLocation("entity/shield_base_nopattern"));

    @SubscribeEvent
    public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
        instance = new SteelShieldRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        event.registerReloadListener(instance);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(STEEL_SHIELD, ShieldModel::createLayer);
    }

    public SteelShieldRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
        this.blockEntityRenderDispatcher = p_172550_;
        this.entityModelSet = p_172551_;
    }

    @Override
    public void onResourceManagerReload(ResourceManager p_172555_) {
        this.shieldModel = new ShieldModel(this.entityModelSet.bakeLayer(STEEL_SHIELD));
    }

    @Override
    public void renderByItem(ItemStack p_108830_, ItemDisplayContext p_270899_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_) {
        if (p_108830_.is(RegistryHandler.STEEL_SHIELD.get())) {
            boolean flag = BlockItem.getBlockEntityData(p_108830_) != null;
            p_108832_.pushPose();
            p_108832_.scale(1.0F, -1.0F, -1.0F);
//            Material material = flag ? ModelBakery.SHIELD_BASE : ModelBakery.NO_PATTERN_SHIELD;
            Material material = flag ? STEEL_SHIELD_BASE : STEEL_NO_PATTERN_SHIELD;
//          // maybe I should try delete atlas render...
            VertexConsumer vertexconsumer = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(p_108833_, this.shieldModel.renderType(material.atlasLocation()), true, p_108830_.hasFoil()));
            this.shieldModel.handle().render(p_108832_, vertexconsumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
            if (flag) {
                List<Pair<Holder<BannerPattern>, DyeColor>> list = BannerBlockEntity.createPatterns(ShieldItem.getColor(p_108830_), BannerBlockEntity.getItemPatterns(p_108830_));
                BannerRenderer.renderPatterns(p_108832_, p_108833_, p_108834_, p_108835_, this.shieldModel.plate(), material, false, list, p_108830_.hasFoil());
            } else {
                this.shieldModel.plate().render(p_108832_, vertexconsumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            p_108832_.popPose();
        }

    }
}

