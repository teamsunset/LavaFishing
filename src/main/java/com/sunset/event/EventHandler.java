package com.sunset.event;

import com.sunset.effect.EffectBlessed;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EventHandler
{
    @Mod.EventBusSubscriber
    public class ForgeEvent
    {
        @SubscribeEvent
        public static void onEntityDamage(LivingDamageEvent event) {
            EffectBlessed.onEntityDamaged(event);
        }
    }
}
