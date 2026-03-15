package club.redux.sunset.lavafishing.tool.registry

import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegistryObject
import kotlin.reflect.KProperty

abstract class Registrar<I>(
    private val deferredRegister: DeferredRegister<I>,
) {
    constructor(reg: IForgeRegistry<I>, modId: String) : this(UtilRegister.create(reg, modId))
    constructor(reg: Registry<I>, modId: String) : this(UtilRegister.create(reg.key(), modId))
    constructor(key: ResourceKey<out Registry<I>>, modId: String) : this(UtilRegister.create(key, modId))

    fun attach(bus: IEventBus) = this.deferredRegister.register(bus)

    open fun <T : I> register(name: String, supplier: () -> T): RegistryObject<T> {
        return this.deferredRegister.registerKt(name, supplier)
    }

    @JvmName("stringRegister")
    protected fun <T : I> String.register(supplier: () -> T) = this@Registrar.register(this, supplier)

    protected fun <T : I> register(supplier: (String) -> T) = Delegator(supplier)

    fun getHolders() = this.deferredRegister.getEntries().toList()
    fun getEntries() = this.getHolders().map { it.get() }.toSet()
    inline fun <reified T : I> getEntriesIsInstance() = this.getEntries().filterIsInstance<T>().toSet()

    protected inner class Delegator<T : I>(
        private val supplier: (String) -> T,
    ) {
        private lateinit var registryObject: RegistryObject<T>
        private var post: (RegistryObject<T>) -> Unit = { }

        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): Delegator<T> = this.apply {
            val name = property.name.lowercase()
            this.registryObject = this@Registrar.register(name) { this.supplier(name) }.also(this.post)
        }

        operator fun getValue(thisRef: Any?, property: KProperty<*>): RegistryObject<T> = this.registryObject

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: RegistryObject<T>) {
            throw UnsupportedOperationException("Cannot set value of a Registrar.Delegator")
        }

        fun post(action: (RegistryObject<T>) -> Unit) = this.apply { this.post = action }
    }
}
