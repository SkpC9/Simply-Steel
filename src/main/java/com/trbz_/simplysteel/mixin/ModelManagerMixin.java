package com.trbz_.simplysteel.mixin;

import com.trbz_.simplysteel.SimplySteel;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.AtlasSet;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(ModelManager.class)
public class ModelManagerMixin {
    // signal that we want to inject into a method


    @Shadow
    @Final
    private static Map<ResourceLocation, ResourceLocation> VANILLA_ATLASES;

    @Mutable
    @Shadow
    @Final
    private AtlasSet atlases;

    @Inject(
            method = "<init>(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/color/block/BlockColors;I)V",  // the jvm bytecode signature for the constructor
            at = @At("TAIL")  // signal that this void should be run at the method HEAD, meaning the first opcode
    )
    void constructorHead(
            TextureManager p_119406_, BlockColors p_119407_, int p_119408_,
            // you will need to put any parameters the constructor accepts here, they will be the actual passed values
            // it also needs to accept a special argument that mixin passes to this injection method
            CallbackInfo ci
    ) {
        ResourceLocation STEEL_SHIELD_SHEET = new ResourceLocation(SimplySteel.MOD_ID, "textures/atlas/steel_shield_patterns.png");
        Map<ResourceLocation, ResourceLocation> simplysteel_atlases = Map.of(STEEL_SHIELD_SHEET, new ResourceLocation(SimplySteel.MOD_ID, "steel_shield_patterns"));
        Map<ResourceLocation, ResourceLocation> all_atlases = new HashMap<>(VANILLA_ATLASES);
        all_atlases.putAll(simplysteel_atlases);
        this.atlases = new AtlasSet(all_atlases, p_119406_);
    }
}
