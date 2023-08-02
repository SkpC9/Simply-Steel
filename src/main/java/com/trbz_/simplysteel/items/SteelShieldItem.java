package com.trbz_.simplysteel.items;

import com.trbz_.simplysteel.renderers.SteelShieldRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SteelShieldItem extends ShieldItem {
    public static final ResourceLocation BLOCKING = new ResourceLocation("minecraft:blocking");

    public SteelShieldItem(Item.Properties p_43089_) {
        super(p_43089_);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return SteelShieldRenderer.instance;
            }
        });
    }

}
