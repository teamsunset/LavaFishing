package club.redux.sunset.lavafishing.event


import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.ai.goal.SlingshotGoal
import club.redux.sunset.lavafishing.behavior.BehaviorDispenserBullet
import club.redux.sunset.lavafishing.client.model.ModelBullet
import club.redux.sunset.lavafishing.client.particle.ParticleFirePunch
import club.redux.sunset.lavafishing.client.renderer.blockentity.BlockEntityRendererPrometheusBounty
import club.redux.sunset.lavafishing.client.renderer.entity.EntityRendererBullet
import club.redux.sunset.lavafishing.datagenerator.ModItemModelProvider
import club.redux.sunset.lavafishing.datagenerator.ModItemTagProvider
import club.redux.sunset.lavafishing.datagenerator.ModRecipeProvider
import club.redux.sunset.lavafishing.effect.EffectEndlessFlame
import club.redux.sunset.lavafishing.effect.EffectLavaWalker
import club.redux.sunset.lavafishing.item.ItemPromethiumArmor
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.loot.LootTableHandler
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModParticleTypes
import club.redux.sunset.lavafishing.registry.ModPotions
import com.teammetallurgy.aquaculture.client.ClientHandler
import net.minecraft.client.Minecraft
import net.minecraft.client.particle.SpriteSet
import net.minecraft.data.tags.TagsProvider
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.event.LootTableLoadEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.entity.EntityJoinLevelEvent
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import java.util.concurrent.CompletableFuture

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
            LootTableHandler.onLootTableLoad(event)
        }

        @SubscribeEvent
        fun onEntityJoinLevel(event: EntityJoinLevelEvent) {
            SlingshotGoal.onEntityJoinLevel(event)
        }

        @SubscribeEvent
        fun onPlayerTick(event: TickEvent.PlayerTickEvent) {

        }
    }

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
    object ForgeEventClient {
        @SubscribeEvent
        fun onItemTooltip(event: ItemTooltipEvent) {
            ModTooltip.onItemTooltip(event)
        }
    }

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    object ModEventBoth {
        @SubscribeEvent
        fun onSetup(event: FMLCommonSetupEvent) {
            BehaviorDispenserBullet.onSetup(event)
            ModPotions.onCommonSetupEvent(event)
            ModItems.registerFishData()
        }

        @SubscribeEvent
        fun onGatherDataEvent(event: GatherDataEvent) {
            event.generator.apply {
                addProvider(true, ModRecipeProvider(this.packOutput))
                addProvider(
                    event.includeServer(),
                    ModItemTagProvider(
                        packOutput,
                        event.lookupProvider,
                        CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()),
                        event.existingFileHelper
                    )
                )
                addProvider(event.includeClient(), ModItemModelProvider(packOutput, event.existingFileHelper))
            }
        }
    }

    @EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    object ModEventClient {

        @SubscribeEvent
        fun onClientSetup(event: FMLClientSetupEvent) {
            ItemSlingshot.onClientSetup(event)
            event.enqueueWork { ClientHandler.registerFishingRodModelProperties(ModItems.OBSIDIAN_FISHING_ROD.get()) }
        }

        @SubscribeEvent
        fun onRegisterRenderers(event: RegisterRenderers) {
            BlockEntityRendererPrometheusBounty.onRegisterRenderers(event)
            EntityRendererBullet.onRegisterRenderers(event)
        }

        @SubscribeEvent
        fun onParticleFactoriesRegistry(event: RegisterParticleProvidersEvent?) {
            Minecraft.getInstance().particleEngine.register(
                ModParticleTypes.FIRE_PUNCH.get()
            ) { sprites: SpriteSet? -> ParticleFirePunch.Provider(sprites!!) }
        }

        @SubscribeEvent
        fun onRegisterLayers(event: RegisterLayerDefinitions) {
            event.registerLayerDefinition(ModelBullet.LAYER_LOCATION, ModelBullet::createBodyLayer)
        }
    }
}