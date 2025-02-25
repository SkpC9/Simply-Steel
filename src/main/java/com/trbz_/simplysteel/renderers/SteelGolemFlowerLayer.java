package com.trbz_.simplysteel.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.trbz_.simplysteel.entities.SteelGolem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.Blocks;

public class SteelGolemFlowerLayer extends RenderLayer<SteelGolem, IronGolemModel<SteelGolem>> {
    public SteelGolemFlowerLayer(RenderLayerParent<SteelGolem, IronGolemModel<SteelGolem>> p_117159_) {
        super(p_117159_);
    }

    public void render(PoseStack p_117172_, MultiBufferSource p_117173_, int p_117174_, SteelGolem p_117175_, float p_117176_, float p_117177_, float p_117178_, float p_117179_, float p_117180_, float p_117181_) {
        if (p_117175_.getOfferFlowerTick() != 0) {
            p_117172_.pushPose();
            ModelPart modelpart = this.getParentModel().getFlowerHoldingArm();
            modelpart.translateAndRotate(p_117172_);
            p_117172_.translate(-1.1875D, 1.0625D, -0.9375D);
            p_117172_.translate(0.5D, 0.5D, 0.5D);
            float f = 0.5F;
            p_117172_.scale(0.5F, 0.5F, 0.5F);
            p_117172_.mulPose(Axis.XP.rotationDegrees(-90.0F));
            p_117172_.translate(-0.5D, -0.5D, -0.5D);
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(Blocks.POPPY.defaultBlockState(), p_117172_, p_117173_, p_117174_, OverlayTexture.NO_OVERLAY);
            p_117172_.popPose();
        }
    }
}