package com.trbz_.simplysteel.util;

import com.trbz_.simplysteel.SimplySteel;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimplySteel.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabHandler {
    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(RegistryHandler.STEEL_INGOT.get());
            event.accept(RegistryHandler.CRUDE_IRON.get());
            event.accept(RegistryHandler.STEEL_NUGGET.get());
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(RegistryHandler.STEEL_SHOVEL.get());
            event.accept(RegistryHandler.STEEL_PICKAXE.get());
            event.accept(RegistryHandler.STEEL_AXE.get());
            event.accept(RegistryHandler.STEEL_HOE.get());
            event.accept(RegistryHandler.QUARTZ_AND_STEEL.get());
            event.accept(RegistryHandler.FLINT_AND_IRON.get());
            event.accept(RegistryHandler.QUARTZ_AND_IRON.get());
            event.accept(RegistryHandler.STEEL_SHEARS.get());
        }

        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(RegistryHandler.STEEL_SWORD.get());
            event.accept(RegistryHandler.STEEL_HELMET.get());
            event.accept(RegistryHandler.STEEL_CHESTPLATE.get());
            event.accept(RegistryHandler.STEEL_LEGGINGS.get());
            event.accept(RegistryHandler.STEEL_BOOTS.get());
            event.accept(RegistryHandler.STEEL_SHIELD.get());
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(RegistryHandler.STEEL_BLOCK_ITEM.get());
            event.accept(RegistryHandler.STEEL_BARS_ITEM.get());
            event.accept(RegistryHandler.STEEL_DOOR_ITEM.get());
            event.accept(RegistryHandler.STEEL_TRAPDOOR_ITEM.get());
        }

        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(RegistryHandler.STEEL_DOOR_ITEM.get());
            event.accept(RegistryHandler.STEEL_TRAPDOOR_ITEM.get());
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(RegistryHandler.STEEL_ANVIL_ITEM.get());
            event.accept(RegistryHandler.CHIPPED_STEEL_ANVIL_ITEM.get());
            event.accept(RegistryHandler.DAMAGED_STEEL_ANVIL_ITEM.get());
        }

    }
}
