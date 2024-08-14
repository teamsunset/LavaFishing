package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.item.ItemPromethiumArmor
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.item.fish.ItemLavaFish
import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.data.PackOutput
import net.minecraft.world.item.MobBucketItem
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import org.slf4j.LoggerFactory

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

        fun registerByParentClasses(vararg parentClasses: Class<*>) {
            ModItems.REGISTER.entries
                .filter { entry -> parentClasses.any { parentClass -> parentClass.isInstance(entry.get()) } }
                .forEach { register(it.key!!.location().path) }
        }

        LoggerFactory.getLogger("ModItemModelProvider").info("Registering item models...")

        registerByParentClasses(
            ItemLavaFish::class.java,
            ItemPromethiumArmor::class.java,
            ItemBullet::class.java,
            MobBucketItem::class.java
        )

        // Food
        register("spicy_fish_fillet")

        // Misc
        register("promethium_nugget")
        register("promethium_ingot")
    }
}
