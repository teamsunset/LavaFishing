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
        this.commonModels()
    }

    private fun commonModels() {
        val register = { itemId: String ->
            withExistingParent("item/$itemId", "item/generated")
                .texture("layer0", "item/$itemId")
        }

        // Fish
        register("agni_fish")
        register("obsidian_sword_fish")
        register("flame_squat_lobster")
        register("steam_flying_fish")
        register("arowana_fish")
        register("yeti_crab")
        register("scaly_foot_snail")
        register("quartz_fish")
        register("lava_lamprey")

        // Armor
        register("promethium_helmet")
        register("promethium_chestplate")
        register("promethium_leggings")
        register("promethium_boots")

        // Bullet
        register("stone_bullet")
        register("iron_bullet")
        register("neptunium_bullet")
        register("promethium_bullet")

        // Food
        register("spicy_fish_fillet")

        // Misc
        register("promethium_nugget")
        register("promethium_ingot")
    }
}
