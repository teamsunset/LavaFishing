package club.redux.sunset.lavafishing.event


import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.behavior.BehaviorDispenserPromethiumBullet
import club.redux.sunset.lavafishing.client.model.ModelPromethiumBullet
import club.redux.sunset.lavafishing.client.particle.ParticleFirePunch
import club.redux.sunset.lavafishing.client.renderer.blockentity.BlockEntityRendererPrometheusBounty
import club.redux.sunset.lavafishing.client.renderer.entity.EntityRendererPromethiumBullet
import club.redux.sunset.lavafishing.datagenerator.ModRecipeProvider
import club.redux.sunset.lavafishing.effect.EffectBlessed
import club.redux.sunset.lavafishing.effect.EffectLavaWalker
import club.redux.sunset.lavafishing.item.ItemPromethiumSlingshot
import club.redux.sunset.lavafishing.item.PromethiumArmor
import club.redux.sunset.lavafishing.loot.LootTableHandler
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModParticleTypes
import net.minecraft.client.Minecraft
import net.minecraft.client.particle.SpriteSet
import net.minecraft.world.level.block.DispenserBlock
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.event.LootTableLoadEvent
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

class EventHandler {
    @EventBusSubscriber
    object ForgeEvent {
        @SubscribeEvent
        fun onEntityDamage(event: LivingDamageEvent) {
            EffectBlessed.onEntityDamage(event)
            PromethiumArmor.onEntityDamage(event)
        }

        @SubscribeEvent
        fun onBreakSpeed(event: BreakSpeed) {
            EffectLavaWalker.onBreakSpeed(event)
        }

        @SubscribeEvent
        fun onEntityTick(event: LivingTickEvent) {
            PromethiumArmor.onLivingTick(event)
        }

        @SubscribeEvent
        fun onEntityAttack(event: LivingAttackEvent) {
            PromethiumArmor.onEntityAttack(event)
        }

        @SubscribeEvent
        fun onLootTableLoad(event: LootTableLoadEvent) {
            LootTableHandler.onLootTableLoad(event)
        }
    }

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    object ModEventBoth {
        @SubscribeEvent
        fun onSetup(event: FMLCommonSetupEvent) {
            DispenserBlock.registerBehavior(ModItems.PROMETHIUM_BULLET.get(), BehaviorDispenserPromethiumBullet())
        }

        @SubscribeEvent
        fun onGatherDataEvent(event: GatherDataEvent) {
            event.generator.apply { addProvider(true, ModRecipeProvider(this.packOutput)) }
        }
    }

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    object ModEventClient {
        @SubscribeEvent
        fun onClientSetup(event: FMLClientSetupEvent) {
            ItemPromethiumSlingshot.onClientSetup(event)
        }

        @SubscribeEvent
        fun onRegisterRenderers(event: RegisterRenderers) {
            BlockEntityRendererPrometheusBounty.onRegisterRenderers(event)
            EntityRendererPromethiumBullet.onRegisterRenderers(event)
        }

        @SubscribeEvent
        fun onParticleFactoriesRegistry(event: RegisterParticleProvidersEvent?) {
            Minecraft.getInstance().particleEngine.register(
                ModParticleTypes.FIRE_PUNCH.get()
            ) { sprites: SpriteSet? -> ParticleFirePunch.Provider(sprites!!) }
        }

        @SubscribeEvent
        fun onRegisterLayers(event: RegisterLayerDefinitions) {
            event.registerLayerDefinition(ModelPromethiumBullet.LAYER_LOCATION, ModelPromethiumBullet::createBodyLayer)
        }
    }
}