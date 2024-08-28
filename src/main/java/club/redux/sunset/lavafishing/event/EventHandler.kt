package club.redux.sunset.lavafishing.event


import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.ai.goal.GoalSlingshot
import club.redux.sunset.lavafishing.behavior.BehaviorDispenserBullet
import club.redux.sunset.lavafishing.client.model.ModelBullet
import club.redux.sunset.lavafishing.client.model.ModelFishTest
import club.redux.sunset.lavafishing.client.particle.ParticleFirePunch
import club.redux.sunset.lavafishing.client.renderer.blockentity.BlockEntityRendererPrometheusBounty
import club.redux.sunset.lavafishing.client.renderer.entity.EntityRendererBullet
import club.redux.sunset.lavafishing.effect.EffectEndlessFlame
import club.redux.sunset.lavafishing.effect.EffectLavaWalker
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.item.ItemPromethiumArmor
import club.redux.sunset.lavafishing.item.fish.ItemLavaFish
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModLootTables
import club.redux.sunset.lavafishing.registry.ModParticleTypes
import club.redux.sunset.lavafishing.registry.ModPotions
import net.minecraft.client.particle.SpriteSet
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.client.event.ViewportEvent
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.event.LootTableLoadEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.event.entity.EntityJoinLevelEvent
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

class EventHandler {
    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
    object ForgeEventBoth {
        @SubscribeEvent
        fun onEntityDamage(event: LivingDamageEvent) {
            EffectEndlessFlame.onEntityDamage(event)
            ItemPromethiumArmor.onEntityDamage(event)
        }

        @SubscribeEvent
        fun onBreakSpeed(event: BreakSpeed) {
            EffectLavaWalker.onBreakSpeed(event)
        }

        @SubscribeEvent
        fun onEntityTick(event: LivingTickEvent) {
            ItemPromethiumArmor.onLivingTick(event)
        }

        @SubscribeEvent
        fun onEntityAttack(event: LivingAttackEvent) {
            ItemPromethiumArmor.onEntityAttack(event)
        }

        @SubscribeEvent
        fun onLootTableLoad(event: LootTableLoadEvent) {
            ModLootTables.onLootTableLoad(event)
        }

        @SubscribeEvent
        fun onEntityJoinLevel(event: EntityJoinLevelEvent) {
            GoalSlingshot.onEntityJoinLevel(event)
        }

        @SubscribeEvent
        fun onPlayerTick(event: TickEvent.PlayerTickEvent) {

        }
    }

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
    object ForgeEventClient {
        @SubscribeEvent
        fun onItemTooltip(event: ItemTooltipEvent) {
            EventTooltip.onItemTooltip(event)
        }

        @SubscribeEvent
        fun onFogRender(event: ViewportEvent.RenderFog) {
            ItemPromethiumArmor.onFogRender(event)
        }
    }

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    object ModEventBoth {
        @SubscribeEvent
        fun onSetup(event: FMLCommonSetupEvent) {
            BehaviorDispenserBullet.onSetup(event)
            ModPotions.onCommonSetupEvent(event)
            ItemLavaFish.onSetup(event)
            EntityLavaFish.onSetup(event)
        }

        @SubscribeEvent
        fun onSpawnPlacementRegister(event: SpawnPlacementRegisterEvent) {
            EntityLavaFish.onSpawnPlacementRegister(event)
        }

        @SubscribeEvent
        fun onGatherData(event: GatherDataEvent) {
            EventDataGenerator.onGatherData(event)
        }

        @SubscribeEvent
        fun onEntityAttributeCreation(event: EntityAttributeCreationEvent) {
            EntityLavaFish.onEntityAttributeCreation(event)
        }
    }

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    object ModEventClient {

        @SubscribeEvent
        fun onClientSetup(event: FMLClientSetupEvent) {
            ItemSlingshot.onClientSetup(event)
            ModItems.onClientSetup(event)
        }

        @SubscribeEvent
        fun onRegisterRenderers(event: RegisterRenderers) {
            BlockEntityRendererPrometheusBounty.onRegisterRenderers(event)
            EntityRendererBullet.onRegisterRenderers(event)
        }

        @SubscribeEvent
        fun onRegisterEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
            EntityLavaFish.onRegisterEntityRenderers(event)
        }

        @SubscribeEvent
        fun onRegisterParticleProviders(event: RegisterParticleProvidersEvent) {
            event.registerSpriteSet(ModParticleTypes.FIRE_PUNCH.get()) { sprites: SpriteSet ->
                ParticleFirePunch.Provider(sprites)
            }
        }


        @SubscribeEvent
        fun onRegisterLayerDefinitions(event: RegisterLayerDefinitions) {
            ModelBullet.onRegisterLayerDefinitions(event)
            ModelFishTest.onRegisterLayerDefinitions(event)
        }
    }
}