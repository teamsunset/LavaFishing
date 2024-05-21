package club.redux.sunset.lavafishing.datagenerator

import net.minecraft.data.PackOutput
import net.minecraft.data.loot.LootTableProvider

class ModLootTableProvider(
    pOutput: PackOutput,
) : LootTableProvider(pOutput, emptySet(), emptyList()) {
//    override fun getTables(): MutableList<SubProviderEntry> {
//        val build =
//            { resourceLocation: ResourceLocation, builder: LootTable.Builder, lootContextParamSet: LootContextParamSet ->
//                SubProviderEntry(
//                    { LootTableSubProvider { it.accept(resourceLocation, builder) } },
//                    lootContextParamSet
//                )
//            }
//        return mutableListOf(
//            build(
//                ModResourceLocation("gameplay/fishing/lava/fish"),
//                LootTable.lootTable().withPool(
//                    LootPool.lootPool()
//                ),
//                LootContextParamSets.FISHING
//            ),
//        )
//    }
}