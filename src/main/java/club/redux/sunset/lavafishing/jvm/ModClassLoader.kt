package club.redux.sunset.lavafishing.jvm

class ModClassLoader : ClassLoader() {
    override fun findClass(name: String?): Class<*> {
        return super.findClass(name)
    }
}