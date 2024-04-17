package club.redux.sunset.lavafishing.event;

import club.asynclab.web.BuildConstants;
import club.redux.sunset.lavafishing.client.particle.ParticleFirePunch;
import club.redux.sunset.lavafishing.client.renderer.blockentity.BlockEntityRendererPrometheusBounty;
import club.redux.sunset.lavafishing.client.renderer.entity.EntityObsidianHookRenderer;
import club.redux.sunset.lavafishing.effect.EffectBlessed;
import club.redux.sunset.lavafishing.effect.EffectLavaWalker;
import club.redux.sunset.lavafishing.item.ItemObsidianFishingRod;
import club.redux.sunset.lavafishing.item.PromethiumArmor;
import club.redux.sunset.lavafishing.loot.LootTableHandler;
import club.redux.sunset.lavafishing.util.RegistryCollection.BlockEntityTypeCollection;
import club.redux.sunset.lavafishing.util.RegistryCollection.EntityTypeCollection;
import club.redux.sunset.lavafishing.util.RegistryCollection.ParticleTypeCollection;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class EventHandler {
    @Mod.EventBusSubscriber
    public static class ForgeEvent {
        @SubscribeEvent
        public static void onEntityDamage(LivingDamageEvent event) {
            EffectBlessed.onEntityDamaged(event);
        }

        @SubscribeEvent
        public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
            EffectLavaWalker.onPlayerBreakSpeed(event);
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            PromethiumArmor.onPlayerTick(event);
        }

        @SubscribeEvent
        public static void onLootTableLoad(LootTableLoadEvent event) {
            LootTableHandler.onLootTableLoad(event);
        }
    }

    @Mod.EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBoth {

    }

    @Mod.EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEventClient {
        @SubscribeEvent
        public static void setupClient(FMLClientSetupEvent event) {
            ItemObsidianFishingRod.propertyOverrideRegistry(event);
        }

        @SubscribeEvent
        public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityTypeCollection.ENTITY_OBSIDIAN_HOOK.get(), EntityObsidianHookRenderer::new);
            event.registerBlockEntityRenderer(BlockEntityTypeCollection.BLOCK_ENTITY_PROMETHEUS_BOUNTY.get(), BlockEntityRendererPrometheusBounty::new);
        }

        @SubscribeEvent
        public static void onParticleFactoriesRegistry(final RegisterParticleProvidersEvent event) {
            Minecraft.getInstance().particleEngine.register(ParticleTypeCollection.PARTICLE_FIRE_PUNCH.get(), ParticleFirePunch.Provider::new);
        }
    }
}
