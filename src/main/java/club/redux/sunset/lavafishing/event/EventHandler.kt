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
import club.redux.sunset.lavafishing.item.block.BlockItemWithoutLevelRenderer
import club.redux.sunset.lavafishing.item.fish.ItemLavaFish
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModLootTables
import club.redux.sunset.lavafishing.registry.ModParticleTypes
import club.redux.sunset.lavafishing.registry.ModPotions
import net.minecraft.client.particle.SpriteSet
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent
import net.neoforged.neoforge.client.event.ViewportEvent
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
import net.neoforged.neoforge.data.event.GatherDataEvent
import net.neoforged.neoforge.event.LootTableLoadEvent
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.event.tick.EntityTickEvent

class EventHandler {
    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    object ForgeEventBoth {
        @SubscribeEvent
        fun onLivingDamagePre(event: LivingDamageEvent.Pre) {
            EffectEndlessFlame.onLivingDamagePre(event)
            ItemPromethiumArmor.onLivingDamagePre(event)
        }

        @SubscribeEvent
        fun onBreakSpeed(event: PlayerEvent.BreakSpeed) {
            EffectLavaWalker.onBreakSpeed(event)
        }

        @SubscribeEvent
        fun onEntityTickPost(event: EntityTickEvent.Post) {
            ItemPromethiumArmor.onEntityTickPost(event)
        }

        @SubscribeEvent
        fun onLivingIncomingDamage(event: LivingIncomingDamageEvent) {
            ItemPromethiumArmor.onLivingIncomingDamage(event)
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
        fun onRegisterBrewingRecipes(event: RegisterBrewingRecipesEvent) {
            ModPotions.onRegisterBrewingRecipes(event)
        }
    }

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = [Dist.CLIENT])
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
            ItemLavaFish.onSetup(event)
        }

        @SubscribeEvent
        fun onRegisterSpawnPlacements(event: RegisterSpawnPlacementsEvent) {
            EntityLavaFish.onRegisterSpawnPlacements(event)
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
        fun onRegisterRenderers(event: EntityRenderersEvent.RegisterRenderers) {
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
        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            ModelBullet.onRegisterLayerDefinitions(event)
            ModelFishTest.onRegisterLayerDefinitions(event)
        }

        @SubscribeEvent
        fun onRegisterClientExtensions(event: RegisterClientExtensionsEvent) {
            BlockItemWithoutLevelRenderer.onRegisterClientExtensions(event)
        }
    }
}