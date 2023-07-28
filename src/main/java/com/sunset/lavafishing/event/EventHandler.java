package com.sunset.lavafishing.event;

import com.sunset.lavafishing.effect.EffectBlessed;
import com.sunset.lavafishing.effect.EffectLavaWalker;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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

        @SubscribeEvent
        public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
            EffectLavaWalker.onPlayerBreakSpeed(event);
        }
    }
}
