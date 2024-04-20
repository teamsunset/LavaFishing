package club.redux.sunset.lavafishing.event

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.client.particle.ParticleFirePunch
import club.redux.sunset.lavafishing.client.renderer.blockentity.BlockEntityRendererPrometheusBounty
import club.redux.sunset.lavafishing.effect.EffectBlessed.Companion.onEntityDamaged
import club.redux.sunset.lavafishing.effect.EffectLavaWalker
import club.redux.sunset.lavafishing.item.PromethiumArmor
import club.redux.sunset.lavafishing.loot.LootTableHandler
import club.redux.sunset.lavafishing.registry.RegistryBlockEntityType
import club.redux.sunset.lavafishing.registry.RegistryParticleType
import net.minecraft.client.Minecraft
import net.minecraft.client.particle.SpriteSet
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.event.LootTableLoadEvent
import net.minecraftforge.event.TickEvent.PlayerTickEvent
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

class EventHandler {
    @EventBusSubscriber
    object ForgeEvent {
        @SubscribeEvent
        fun onEntityDamage(event: LivingDamageEvent) {
            onEntityDamaged(event)
        }

        @SubscribeEvent
        fun onPlayerBreakSpeed(event: BreakSpeed) {
            EffectLavaWalker.onPlayerBreakSpeed(event)
        }

        @SubscribeEvent
        fun onPlayerTick(event: PlayerTickEvent) {
            PromethiumArmor.onPlayerTick(event)
        }

        @SubscribeEvent
        fun onLootTableLoad(event: LootTableLoadEvent) {
            LootTableHandler.onLootTableLoad(event)
        }
    }

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    object ModEventBoth

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    object ModEventClient {
        @SubscribeEvent
        fun setupClient(event: FMLClientSetupEvent) {
        }

        @SubscribeEvent
        fun registerEntityRenders(event: RegisterRenderers) {
            event.registerBlockEntityRenderer(RegistryBlockEntityType.PROMETHEUS_BOUNTY.get()) { context ->
                BlockEntityRendererPrometheusBounty(context)
            }
        }

        @SubscribeEvent
        fun onParticleFactoriesRegistry(event: RegisterParticleProvidersEvent?) {
            Minecraft.getInstance().particleEngine.register(
                RegistryParticleType.FIRE_PUNCH.get()
            ) { sprites: SpriteSet? -> ParticleFirePunch.Provider(sprites!!) }
        }
    }
}