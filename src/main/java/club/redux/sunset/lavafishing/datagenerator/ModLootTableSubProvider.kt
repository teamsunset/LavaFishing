package club.redux.sunset.lavafishing.datagenerator

import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.LootTable
import java.util.function.BiConsumer

class ModLootTableSubProvider : LootTableSubProvider {
    override fun generate(pOutput: BiConsumer<ResourceLocation, LootTable.Builder>) {
    }

}