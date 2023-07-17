package com.trbz_.simplysteel.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.trbz_.simplysteel.SimplySteel;
import com.trbz_.simplysteel.entities.SteelGolem;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.IronGolemCrackinessLayer;
import net.minecraft.client.renderer.entity.layers.IronGolemFlowerLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;

public class SteelGolemRenderer extends MobRenderer<SteelGolem, IronGolemModel<SteelGolem>> {

    public static final ResourceLocation GOLEM_LOCATION = new ResourceLocation(SimplySteel.MOD_ID + ":textures/entity/steel_golem/steel_golem.png");

    public SteelGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new IronGolemModel<>(context.bakeLayer(ModelLayers.IRON_GOLEM)), 0.7F);
        this.addLayer(new SteelGolemCrackinessLayer(this));
        this.addLayer(new SteelGolemFlowerLayer(this));
    }

    public ResourceLocation getTextureLocation(SteelGolem p_115012_) {
        return GOLEM_LOCATION;
    }

    protected void setupRotations(SteelGolem p_115014_, PoseStack p_115015_, float p_115016_, float p_115017_, float p_115018_) {
        super.setupRotations(p_115014_, p_115015_, p_115016_, p_115017_, p_115018_);
        if (!((double)p_115014_.walkAnimation.speed() < 0.01D)) {
            float f = 13.0F;
            float f1 = p_115014_.walkAnimation.position(p_115018_) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            p_115015_.mulPose(Axis.ZP.rotationDegrees(6.5F * f2));
        }
    }

}
