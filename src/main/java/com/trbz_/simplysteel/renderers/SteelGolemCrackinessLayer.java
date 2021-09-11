package com.trbz_.simplysteel.renderers;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.trbz_.simplysteel.SimplySteel;
import com.trbz_.simplysteel.entities.SteelGolem;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;

import java.util.Map;

public class SteelGolemCrackinessLayer extends RenderLayer<SteelGolem, IronGolemModel<SteelGolem>> {

    private static final Map<IronGolem.Crackiness, ResourceLocation> resourceLocations = ImmutableMap.of(IronGolem.Crackiness.LOW, new ResourceLocation(SimplySteel.MOD_ID + ":textures/entity/steel_golem/steel_golem_crackiness_low.png"), IronGolem.Crackiness.MEDIUM, new ResourceLocation(SimplySteel.MOD_ID + ":textures/entity/steel_golem/steel_golem_crackiness_medium.png"), IronGolem.Crackiness.HIGH, new ResourceLocation(SimplySteel.MOD_ID + ":textures/entity/steel_golem/steel_golem_crackiness_high.png"));

    public SteelGolemCrackinessLayer(RenderLayerParent<SteelGolem, IronGolemModel<SteelGolem>> p_117135_) {
        super(p_117135_);
    }

    @Override
    public void render(PoseStack p_117148_, MultiBufferSource p_117149_, int p_117150_, SteelGolem p_117151_, float p_117152_, float p_117153_, float p_117154_, float p_117155_, float p_117156_, float p_117157_) {
        if (!p_117151_.isInvisible()) {
            IronGolem.Crackiness irongolem$crackiness = p_117151_.getCrackiness();
            if (irongolem$crackiness != IronGolem.Crackiness.NONE) {
                ResourceLocation resourcelocation = resourceLocations.get(irongolem$crackiness);
                renderColoredCutoutModel(this.getParentModel(), resourcelocation, p_117148_, p_117149_, p_117150_, p_117151_, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
