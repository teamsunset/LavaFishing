package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.tool.datagenerator.DataProviderBiomeModifier
import net.minecraft.data.PackOutput
import net.minecraft.tags.BiomeTags


class ModDataProviderBiomeModifier(
    output: PackOutput,
) : DataProviderBiomeModifier(output, BuiltConstants.MOD_ID) {
    override fun addModifiers() {
        addModifier(
            "nether_spawn", ModifierAddSpawns(
                BiomeTags.IS_NETHER,
                ModEntityTypes.getHoldersByEntityClass<EntityLavaFish>().map {
                    ModifierAddSpawns.Spawner(it.get(), 1, 1, 3)
                }
            ))
    }
}
