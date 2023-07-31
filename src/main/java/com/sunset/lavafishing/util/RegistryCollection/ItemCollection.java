package com.sunset.lavafishing.util.RegistryCollection;

import com.sunset.lavafishing.item.BlockItemWithoutLevelRenderer;
import com.sunset.lavafishing.item.ItemObsidianFishingRod;
import com.sunset.lavafishing.item.PromethiumArmor;
import com.sunset.lavafishing.item.fishes.*;
import com.sunset.lavafishing.util.LavaArmorMaterials;
import com.sunset.lavafishing.util.Reference;
import com.teammetallurgy.aquaculture.item.SimpleItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.teammetallurgy.aquaculture.api.AquacultureAPI.FISH_DATA;

public class ItemCollection
{

    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    public static final RegistryObject<Item> ITEM_OBSIDIAN_FISHING_ROD = ITEM_DEFERRED_REGISTER.register("obsidian_fishing_rod", ItemObsidianFishingRod::new);


    //fishes
    public static final RegistryObject<Item> ITEM_QUARTZ_FISH = ITEM_DEFERRED_REGISTER.register("quartz_fish", ItemQuartzFish::new);
    public static final RegistryObject<Item> ITEM_FLAME_SQUAT_LOBSTER = ITEM_DEFERRED_REGISTER.register("flame_squat_lobster", ItemFlameSquatLobster::new);
    public static final RegistryObject<Item> ITEM_OBSIDIAN_SWORD_FISH = ITEM_DEFERRED_REGISTER.register("obsidian_sword_fish", ItemObsidianSwordFish::new);
    public static final RegistryObject<Item> ITEM_STEAM_FLYING_FISH = ITEM_DEFERRED_REGISTER.register("steam_flying_fish", ItemSteamFlyingFish::new);
    public static final RegistryObject<Item> ITEM_AROWANA_FISH = ITEM_DEFERRED_REGISTER.register("arowana_fish", ItemArowanaFish::new);
    public static final RegistryObject<Item> ITEM_AGNI_FISH = ITEM_DEFERRED_REGISTER.register("agni_fish", ItemAgniFish::new);

    //FISH_DATA
    public static void registerFishData() {
        FISH_DATA.add(ITEM_QUARTZ_FISH.get(), 100, 200, 6);
        FISH_DATA.add(ITEM_FLAME_SQUAT_LOBSTER.get(), 100, 200, 6);
        FISH_DATA.add(ITEM_OBSIDIAN_SWORD_FISH.get(), 100, 200, 6);
        FISH_DATA.add(ITEM_STEAM_FLYING_FISH.get(), 100, 200, 6);
        FISH_DATA.add(ITEM_AROWANA_FISH.get(), 100, 200, 6);
        FISH_DATA.add(ITEM_AGNI_FISH.get(), 100, 200, 6);
    }

    //armor
    public static final RegistryObject<Item> PROMETHIUM_INGOT = ITEM_DEFERRED_REGISTER.register("promethium_ingot", () -> new SimpleItem(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> ITEM_PROMETHIUM_NUGGET = ITEM_DEFERRED_REGISTER.register("promethium_nugget", () -> new SimpleItem(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> ITEM_PROMETHIUM_HELMET = ITEM_DEFERRED_REGISTER.register("promethium_helmet", () -> new PromethiumArmor(LavaArmorMaterials.PROMETHIUM, ArmorItem.Type.HELMET).setArmorTexture("promethium_layer_1"));
    public static final RegistryObject<Item> ITEM_PROMETHIUM_CHESTPLATE = ITEM_DEFERRED_REGISTER.register("promethium_chestplate", () -> new PromethiumArmor(LavaArmorMaterials.PROMETHIUM, ArmorItem.Type.CHESTPLATE).setArmorTexture("promethium_layer_1"));
    public static final RegistryObject<Item> ITEM_PROMETHIUM_LEGGINGS = ITEM_DEFERRED_REGISTER.register("promethium_leggings", () -> new PromethiumArmor(LavaArmorMaterials.PROMETHIUM, ArmorItem.Type.LEGGINGS).setArmorTexture("promethium_layer_2"));
    public static final RegistryObject<Item> ITEM_PROMETHIUM_BOOTS = ITEM_DEFERRED_REGISTER.register("promethium_boots", () -> new PromethiumArmor(LavaArmorMaterials.PROMETHIUM, ArmorItem.Type.BOOTS).setArmorTexture("promethium_layer_1"));

    //other
    public static final RegistryObject<Item> ITEM_BLOCK_PROMETHEUS_BOUNTY = ITEM_DEFERRED_REGISTER.register("prometheus_bounty", () -> new BlockItemWithoutLevelRenderer(BlockCollection.BLOCK_PROMETHEUS_BOUNTY.get(), new Item.Properties().fireResistant()));
}
