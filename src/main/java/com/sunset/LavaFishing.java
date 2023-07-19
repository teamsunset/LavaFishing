package com.sunset;

import com.mojang.logging.LogUtils;
import com.sunset.util.Reference;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class LavaFishing
{
    public static final Logger SG_LOGGER = LogUtils.getLogger();

    public LavaFishing()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
