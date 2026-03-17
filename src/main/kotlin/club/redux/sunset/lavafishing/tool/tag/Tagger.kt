package club.redux.sunset.lavafishing.tool.tag

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import kotlin.reflect.KProperty

abstract class Tagger<T>(private val registry: ResourceKey<out Registry<T>>, private val modId: String) {
    protected fun tag(path: String): TagKey<T> =
        TagKey.create(registry, ResourceLocation(modId, path))

    @JvmName("stringTag")
    protected fun String.tag() = this@Tagger.tag(this)
    protected fun tag() = Delegator()


    protected inner class Delegator {
        private lateinit var tagKey: TagKey<T>

        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): Delegator = this.apply {
            val path = property.name.lowercase()
            this.tagKey = this@Tagger.tag(path)
        }

        operator fun getValue(thisRef: Any?, property: KProperty<*>): TagKey<T> = this.tagKey

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: TagKey<T>) {
            throw UnsupportedOperationException("Cannot set value of a Tagger.Delegator")
        }
    }
}