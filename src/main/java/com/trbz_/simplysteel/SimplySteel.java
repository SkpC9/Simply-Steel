package com.trbz_.simplysteel;


import com.trbz_.simplysteel.entities.SteelGolem;
import com.trbz_.simplysteel.events.SimplySteelEvents;
import com.trbz_.simplysteel.setup.ClientProxy;
import com.trbz_.simplysteel.setup.IProxy;
import com.trbz_.simplysteel.setup.ServerProxy;
import com.trbz_.simplysteel.util.RegistryHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("simplysteel")
public class SimplySteel {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "simplysteel";
    private static IProxy proxy;

    public SimplySteel() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

        MinecraftForge.EVENT_BUS.register(new SimplySteelEvents());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityAttributeCreationEvent);

    }

    @SubscribeEvent
    public void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
        event.put(SteelGolem.TYPE, SteelGolem.MAP);
    }

    private void setup(final FMLCommonSetupEvent event) {

        proxy.init();

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.STEEL_BARS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.STEEL_DOOR.get(), RenderType.translucent());

    }
}
