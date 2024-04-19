package club.redux.sunset.lavafishing.loot;

import club.asynclab.web.BuildConstants;
import com.teammetallurgy.aquaculture.init.AquaLootTables;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.event.LootTableLoadEvent;

import java.util.HashMap;
import java.util.Map;

public class LootTableHandler {
    public static final ResourceLocation FISH = register("gameplay/fishing/fish");

    //
    public static final ResourceLocation LAVA_FISHING = register("gameplay/fishing/lava/fishing");
    public static final ResourceLocation LAVA_FISH = register("gameplay/fishing/lava/fish");
    public static final ResourceLocation LAVA_JUNK = register("gameplay/fishing/lava/junk");
    public static final ResourceLocation LAVA_TREASURE = register("gameplay/fishing/lava/treasure");
    public static final ResourceLocation NETHER_FISHING = register("gameplay/fishing/nether/fishing");
    public static final ResourceLocation NETHER_FISH = register("gameplay/fishing/nether/fish");
    public static final ResourceLocation NETHER_JUNK = register("gameplay/fishing/nether/junk");
    public static final ResourceLocation NETHER_TREASURE = register("gameplay/fishing/nether/treasure");

    private static ResourceLocation register(String path) {
        return BuiltInLootTables.register(new ResourceLocation(BuildConstants.MOD_ID, path));
    }

    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();

        Map<ResourceLocation, ResourceLocation> LootTablesInjects = new HashMap<>();
        LootTablesInjects.put(AquaLootTables.LAVA_FISHING, LootTableHandler.LAVA_FISHING);
        LootTablesInjects.put(AquaLootTables.LAVA_FISH, LootTableHandler.LAVA_FISH);
        LootTablesInjects.put(AquaLootTables.LAVA_JUNK, LootTableHandler.LAVA_JUNK);
        LootTablesInjects.put(AquaLootTables.LAVA_TREASURE, LootTableHandler.LAVA_TREASURE);
        LootTablesInjects.put(AquaLootTables.NETHER_FISHING, LootTableHandler.NETHER_FISHING);
        LootTablesInjects.put(AquaLootTables.NETHER_FISH, LootTableHandler.NETHER_FISH);
        LootTablesInjects.put(AquaLootTables.NETHER_JUNK, LootTableHandler.NETHER_JUNK);
        LootTablesInjects.put(AquaLootTables.NETHER_TREASURE, LootTableHandler.NETHER_TREASURE);

        for (ResourceLocation i : LootTablesInjects.keySet()) {
            if (name.equals(i)) {
                event.getTable().removePool("main");
                event.getTable().addPool(
                        new LootPool.Builder()
                                .add(LootTableReference.lootTableReference(LootTablesInjects.get(i)))
                                .name(BuildConstants.MOD_ID + "_inject")
                                .build());
            }
        }

    }
}
