package com.sunset.util.RegistryCollections;

import com.sunset.item.ItemObsidianFishingRod;
import com.sunset.item.fishes.*;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemCollection
{
    public static final List<Item> RegistryItems = new ArrayList<>();
    public static final ItemObsidianFishingRod OBSIDIAN_FISHING_ROD = register(new ItemObsidianFishingRod(), "obsidian_fishing_rod");
    public static final ItemQuartzFish ITEM_QUARTZ_FISH = register(new ItemQuartzFish(), "quartz_fish");
    public static final ItemFlameSquatLobster ITEM_FLAME_SQUAT_LOBSTER = register(new ItemFlameSquatLobster(), "flame_squat_lobster");
    public static final ItemObsidianSwordFish ITEM_OBSIDIAN_SWORD_FISH = register(new ItemObsidianSwordFish(), "obsidian_sword_fish");
    public static final ItemSteamFlyingFish ITEM_STEAM_FLYING_FISH = register(new ItemSteamFlyingFish(), "steam_flying_fish");
    public static final ItemArowanaFish ITEM_AROWANA_FISH = register(new ItemArowanaFish(), "arowana_fish");
    public static final ItemAgniFish ITEM_AGNI_FISH = register(new ItemAgniFish(), "agni_fish");
    

    public static <T extends Item> T register(T item, String registryName) {
        item.setRegistryName(registryName);
        RegistryItems.add(item);
        return item;
    }
}
