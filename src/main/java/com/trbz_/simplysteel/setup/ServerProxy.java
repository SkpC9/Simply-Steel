package com.trbz_.simplysteel.setup;

import com.trbz_.simplysteel.SimplySteel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimplySteel.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class ServerProxy implements IProxy {

    @Override
    public void init() {

    }

//    @Override
//    public ClientLevel getClientWorld() {
//        throw new IllegalStateException("Cannot be run on server!");
//    }
}