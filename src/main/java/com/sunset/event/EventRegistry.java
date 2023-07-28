package com.sunset.event;

import com.sunset.client.particle.ParticleFirePunch;
import com.sunset.client.renderer.entity.EntityObsidianHookRenderer;
import com.sunset.item.ItemObsidianFishingRod;
import com.sunset.util.ModBrewingRecipe;
import com.sunset.util.Reference;
import com.sunset.util.RegistryCollections.EntityTypeCollection;
import com.sunset.util.RegistryCollections.ItemCollection;
import com.sunset.util.RegistryCollections.ParticleTypeCollection;
import com.sunset.util.RegistryCollections.PotionCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


public class EventRegistry
{
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModEventBoth
    {

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(Potions.AWKWARD, ItemCollection.ITEM_AROWANA_FISH.get(), Potions.LUCK)));
            event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(Potions.AWKWARD, ItemCollection.ITEM_FLAME_SQUAT_LOBSTER.get(), Potions.FIRE_RESISTANCE)));
            event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(Potions.AWKWARD, ItemCollection.ITEM_STEAM_FLYING_FISH.get(), PotionCollection.POTION_LAVA_WALKER.get())));
        }

    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ModEventClient
    {
        @SubscribeEvent
        public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
            ItemObsidianFishingRod.propertyOverrideRegistry(event);
        }

        @SubscribeEvent
        public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityTypeCollection.ENTITY_OBSIDIAN_HOOK.get(), EntityObsidianHookRenderer::new);
        }

        @SubscribeEvent
        public static void onParticleFactoriesRegistry(final RegisterParticleProvidersEvent event) {
            Minecraft.getInstance().particleEngine.register(ParticleTypeCollection.PARTICLE_FIRE_PUNCH.get(), ParticleFirePunch.Provider::new);
        }
    }
}
