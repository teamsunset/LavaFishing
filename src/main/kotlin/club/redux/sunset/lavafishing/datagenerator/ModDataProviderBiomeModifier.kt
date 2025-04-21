package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.api.dagagenerator.DataProviderBiomeModifier
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import net.minecraft.data.PackOutput
import net.minecraft.tags.BiomeTags


class ModDataProviderBiomeModifier(
    output: PackOutput,
) : DataProviderBiomeModifier(output, BuildConstants.MOD_ID) {
    override fun addModifiers() {
        addModifier("nether_spawn", ModifierAddSpawns(
            BiomeTags.IS_NETHER,
            ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java).map {
                ModifierAddSpawns.Spawner(it.get(), 1, 1, 3)
            }
        ))
    }
}
