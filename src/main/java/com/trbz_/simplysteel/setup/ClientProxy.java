package com.trbz_.simplysteel.setup;

import com.trbz_.simplysteel.SimplySteel;
import com.trbz_.simplysteel.items.SteelShieldItem;
import com.trbz_.simplysteel.renderers.SteelGolemRenderer;
import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimplySteel.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxy implements IProxy {


    @Override
    public void init() {

        EntityRenderers.register(RegistryHandler.FALLING_ANVIL.get(),
                FallingBlockRenderer::new);

        EntityRenderers.register(RegistryHandler.STEEL_GOLEM.get(),
                SteelGolemRenderer::new);

        initShields();
    }

    private static void initShields() {
        // for rotated model when blocking
        ItemProperties.register(RegistryHandler.STEEL_SHIELD.get(), SteelShieldItem.BLOCKING, (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }

    //    @Override
    public ClientLevel getClientWorld() {
        return Minecraft.getInstance().level;
    }
}