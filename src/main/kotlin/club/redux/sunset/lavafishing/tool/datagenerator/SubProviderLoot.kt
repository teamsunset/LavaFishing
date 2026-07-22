package club.redux.sunset.lavafishing.tool.datagenerator

import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.storage.loot.LootTable
import java.util.function.BiConsumer

abstract class SubProviderLoot(protected val lookupProvider: HolderLookup.Provider) : LootTableSubProvider {
    data class TableBuilderEntry(
        val key: ResourceKey<LootTable>,
        val builder: LootTable.Builder,
    ) {
        fun output(pOutput: BiConsumer<ResourceKey<LootTable>, LootTable.Builder>) {
            pOutput.accept(this.key, this.builder)
        }
    }

    private val tables = mutableListOf<TableBuilderEntry>()

    protected fun createKey(modId: String, path: String): ResourceKey<LootTable> {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(modId, path))
    }

    protected fun add(vararg table: TableBuilderEntry) = this.tables.addAll(table)
    protected fun addAll(tables: List<TableBuilderEntry>) = this.tables.addAll(tables)
    abstract fun generate()
    override fun generate(pOutput: BiConsumer<ResourceKey<LootTable>, LootTable.Builder>) {
        this.generate()
        this.tables.forEach { it.output(pOutput) }
    }
}
