package com.sunset.loot;

import com.sunset.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class LootTableHandler
{
    public static final ResourceLocation FISH = register("gameplay/fishing/fish");

    private static ResourceLocation register(String path) {
        return BuiltInLootTables.register(new ResourceLocation(Reference.MOD_ID, path));
    }
}
