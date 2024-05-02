package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuildConstants
import net.minecraft.data.PackOutput
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class ModItemModelProvider(
    packOutput: PackOutput,
    existingFileHelper: ExistingFileHelper,
) : ItemModelProvider(packOutput, BuildConstants.MOD_ID, existingFileHelper) {

    override fun registerModels() {
    }
}