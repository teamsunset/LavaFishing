package club.redux.sunset.lavafishing.tool.datagenerator

import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.LootTable
import java.util.function.BiConsumer

abstract class SubProviderLoot : LootTableSubProvider {
    data class TableBuilderEntry(
        val location: ResourceLocation,
        val builder: LootTable.Builder,
    ) {
        fun output(output: BiConsumer<ResourceLocation, LootTable.Builder>) {
            output.accept(this.location, this.builder)
        }
    }

    private val tables = mutableListOf<TableBuilderEntry>()

    protected fun createLocation(modId: String, path: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(modId, path)
    }

    protected fun add(vararg table: TableBuilderEntry) = this.tables.addAll(table)
    protected fun addAll(tables: List<TableBuilderEntry>) = this.tables.addAll(tables)

    abstract fun generate()

    override fun generate(output: BiConsumer<ResourceLocation, LootTable.Builder>) {
        this.generate()
        this.tables.forEach { it.output(output) }
    }
}
