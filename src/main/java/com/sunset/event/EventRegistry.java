package com.sunset.event;

import com.sunset.client.particle.ParticleFirePunch;
import com.sunset.client.renderer.entity.EntityObsidianHookRenderer;
import com.sunset.util.ModBrewingRecipe;
import com.sunset.util.Reference;
import com.sunset.util.RegistryCollections.EntityTypeCollection;
import com.sunset.util.RegistryCollections.ItemCollection;
import com.sunset.util.RegistryCollections.ParticleTypeCollection;
import com.sunset.util.RegistryCollections.PotionCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.sunset.util.RegistryCollections.EffectCollection.RegistryEffects;
import static com.sunset.util.RegistryCollections.EntityTypeCollection.RegistryEntities;
import static com.sunset.util.RegistryCollections.ItemCollection.OBSIDIAN_FISHING_ROD;
import static com.sunset.util.RegistryCollections.ItemCollection.RegistryItems;
import static com.sunset.util.RegistryCollections.ParticleTypeCollection.RegistryParticles;
import static com.sunset.util.RegistryCollections.PotionCollection.RegistryPotions;


public class EventRegistry
{
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModEventBoth
    {
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(RegistryItems.toArray(new Item[0]));
        }

        @SubscribeEvent
        public static void onEntityTypeRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().registerAll(RegistryEntities.toArray(new EntityType[0]));
        }

        @SubscribeEvent
        public static void onPotionRegistry(final RegistryEvent.Register<Potion> event) {
            event.getRegistry().registerAll(RegistryPotions.toArray(new Potion[0]));
        }

        @SubscribeEvent
        public static void onMobEffectRegistry(final RegistryEvent.Register<MobEffect> event) {
            event.getRegistry().registerAll(RegistryEffects.toArray(new MobEffect[0]));
        }

        @SubscribeEvent
        public static void onParticleTypeRegistry(final RegistryEvent.Register<ParticleType<?>> event) {
            event.getRegistry().registerAll(RegistryParticles.toArray(new ParticleType[0]));
        }

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(Potions.AWKWARD, ItemCollection.ITEM_AROWANA_FISH, Potions.LUCK)));
            event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(Potions.AWKWARD, ItemCollection.ITEM_FLAME_SQUAT_LOBSTER, Potions.FIRE_RESISTANCE)));
            event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(Potions.AWKWARD, ItemCollection.ITEM_STEAM_FLYING_FISH, PotionCollection.POTION_LAVA_WALKER)));
        }

    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ModEventClient
    {
        @SubscribeEvent
        public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemProperties.register(OBSIDIAN_FISHING_ROD, new ResourceLocation("cast"), (pStack, pLevel, pEntity, pSeed) -> {
                    if (pEntity instanceof Player player) {
                        if (player.getMainHandItem() == pStack) {
                            if (player.fishing != null)
                                return 1.0f;
                        }
                        else if (player.getOffhandItem() == pStack) {
                            if (player.fishing != null)
                                return 1.0f;
                        }
                    }
                    return 0;
                });
            });
        }

        @SubscribeEvent
        public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityTypeCollection.ENTITY_OBSIDIAN_HOOK, EntityObsidianHookRenderer::new);
        }

        @SubscribeEvent
        public static void onParticleFactoriesRegistry(final ParticleFactoryRegisterEvent event) {
            Minecraft.getInstance().particleEngine.register(ParticleTypeCollection.PARTICLE_FIRE_PUNCH, ParticleFirePunch.Provider::new);
        }
    }
}
