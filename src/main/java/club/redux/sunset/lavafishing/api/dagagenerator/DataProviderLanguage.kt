package club.redux.sunset.lavafishing.api.dagagenerator

import net.minecraft.data.PackOutput
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.data.LanguageProvider
import java.util.*

abstract class DataProviderLanguage(
    packOutput: PackOutput,
    modId: String,
    private val locale: Locale,
) : LanguageProvider(packOutput, modId, locale.toString().lowercase()) {
    protected fun choose(vararg pairs: Pair<Locale, String>): String {
        return pairs.find { it.first == this.locale }?.second ?: throw IllegalArgumentException("Locale not found")
    }

    protected fun Item.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))
    protected fun Block.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))
    protected fun EntityType<*>.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))
    protected fun String.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))
    protected fun ItemStack.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))
    protected fun MobEffect.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))

    //    inner class PlaceHolder(private vararg val pairs: Pair<Locale, String>) {
    //        override fun toString(): String {
    //            return choose(*pairs)
    //        }
    //    }
    //
    //    val fish = PlaceHolder(
    //        Locale.PRC to "鱼",
    //        Locale.US to "Fish"
    //    )
}