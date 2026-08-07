package club.redux.sunset.lavafishing.event


import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.ai.goal.GoalSlingshot
import club.redux.sunset.lavafishing.behavior.BehaviorDispenserBullet
import club.redux.sunset.lavafishing.client.model.*
import club.redux.sunset.lavafishing.client.particle.ParticleFirePunch
import club.redux.sunset.lavafishing.client.renderer.blockentity.BlockEntityRendererPrometheusBounty
import club.redux.sunset.lavafishing.client.renderer.entity.EntityRendererBullet
import club.redux.sunset.lavafishing.client.renderer.item.property.ItemPropertySlingshotPull
import club.redux.sunset.lavafishing.effect.EffectEndlessFlame
import club.redux.sunset.lavafishing.effect.EffectLavaWalker
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.item.ItemPromethiumArmor
import club.redux.sunset.lavafishing.item.fish.ItemLavaFish
import club.redux.sunset.lavafishing.misc.ModLootTables
import club.redux.sunset.lavafishing.registry.ModParticleTypes
import club.redux.sunset.lavafishing.registry.ModPotions
import club.redux.sunset.lavafishing.tool.bedrock.BedrockLoader
import net.minecraft.client.particle.SpriteSet
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.client.event.*
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
    @EventBusSubscriber(modid = BuiltConstants.MOD_ID)
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

    @EventBusSubscriber(modid = BuiltConstants.MOD_ID, value = [Dist.CLIENT])
    object ForgeEventClient {
        @SubscribeEvent
        fun onItemTooltip(event: ItemTooltipEvent) {
            EventTooltip.onItemTooltip(event)
        }

        @SubscribeEvent
        fun onFogRender(event: ViewportEvent.RenderFog) {
            ItemPromethiumArmor.onFogRender(event)
        }

        @SubscribeEvent
        fun onRenderBlockScreen(event: RenderBlockScreenEffectEvent) {
            ItemPromethiumArmor.onRenderBlockScreen(event)
        }

    }

    @EventBusSubscriber(modid = BuiltConstants.MOD_ID)
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
        fun onGatherClientData(event: GatherDataEvent.Client) {
            EventDataGenerator.onGatherClientData(event)
        }

        @SubscribeEvent
        fun onGatherServerData(event: GatherDataEvent.Server) {
            EventDataGenerator.onGatherServerData(event)
        }

        @SubscribeEvent
        fun onEntityAttributeCreation(event: EntityAttributeCreationEvent) {
            EntityLavaFish.onEntityAttributeCreation(event)
        }

    }

    @EventBusSubscriber(modid = BuiltConstants.MOD_ID, value = [Dist.CLIENT])
    object ModEventClient {
        @SubscribeEvent
        fun onRegisterRangeSelectItemModelProperties(event: RegisterRangeSelectItemModelPropertyEvent) {
            ItemPropertySlingshotPull.onRegisterRangeSelectItemModelProperties(event)
        }

        @SubscribeEvent
        fun onAddClientReloadListeners(event: AddClientReloadListenersEvent) {
            BedrockLoader.onAddClientReloadListeners(event)
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
            ModelCommonFish.onRegisterLayerDefinitions(event)
            ModelCrab.onRegisterLayerDefinitions(event)
            ModelSwordFish.onRegisterLayerDefinitions(event)
            ModelSnail.onRegisterLayerDefinitions(event)
            ModelEel.onRegisterLayerDefinitions(event)
            ModelLobster.onRegisterLayerDefinitions(event)
        }
    }
}
