package club.redux.sunset.lavafishing.datagenerator

import net.minecraft.data.PackOutput
import net.minecraft.data.loot.LootTableProvider

class ModDataProviderLootTable(
    pOutput: PackOutput,
    subProviders: List<SubProviderEntry>,
) : LootTableProvider(pOutput, emptySet(), subProviders)
