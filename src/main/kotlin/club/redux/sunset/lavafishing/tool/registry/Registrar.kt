package club.redux.sunset.lavafishing.tool.registry

import club.redux.sunset.lavafishing.util.Utils.identifier
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import kotlin.reflect.KProperty

abstract class Registrar<I : Any>(val key: ResourceKey<out Registry<I>>, val modId: String) {
    constructor(reg: Registry<I>, modId: String) : this(reg.key(), modId)

    private val deferredRegister = DeferredRegister.create(key, modId)
    fun attach(bus: IEventBus) = deferredRegister.register(bus)
    open fun <T : I> register(name: String, supplier: () -> T) = this.deferredRegister.register(name, supplier)

    @JvmName("stringRegister")
    protected fun <T : I> String.register(supplier: () -> T) = this@Registrar.register(this, supplier)
    protected fun <T : I> register(supplier: (String) -> T) = this.Delegator(supplier)

    fun getHolders() = this.deferredRegister.entries.toSet()
    inline fun <reified T : I> getHoldersIsInstance() = this.getHolders().filter { it.get() is T }.toSet()
    fun getEntries() = this.getHolders().map { it.get() }.toSet()
    inline fun <reified T : I> getEntriesIsInstance() = this.getEntries().filterIsInstance<T>().toSet()

    protected fun createResourceKey(name: String) = ResourceKey.create(key, modId.identifier(name))

    protected inner class Delegator<T : I>(
        private val supplier: (String) -> T,
    ) {
        private lateinit var deferredHolder: DeferredHolder<I, T>
        private var pre: (String) -> Unit = { }
        private var post: (DeferredHolder<I, T>) -> Unit = {}

        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): Delegator<T> = this.apply {
            val name = property.name.lowercase()
            this.pre(name)
            this.deferredHolder = this@Registrar.register(name) { this.supplier(name) }.also(this.post)
        }

        operator fun getValue(thisRef: Any?, property: KProperty<*>): DeferredHolder<I, T> = this.deferredHolder

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: DeferredHolder<I, T>) {
            throw UnsupportedOperationException("Cannot set value of a Registrar.Delegator")
        }

        fun pre(action: (String) -> Unit) = this.apply { this.pre = action }
        fun post(action: (DeferredHolder<I, T>) -> Unit) = this.apply { this.post = action }
    }
}
