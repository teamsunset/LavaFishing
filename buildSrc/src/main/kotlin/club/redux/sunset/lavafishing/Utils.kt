package club.redux.sunset.lavafishing

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Action
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ExternalModuleDependency

object Utils {
    fun ShadowJar.relocateToShadowed(destinationPrefix: String, vararg paths: String): ShadowJar = apply {
        paths.forEach { relocate(it, "$destinationPrefix.$it") }
    }

    fun ExternalModuleDependency.tee(
        configuration: Configuration,
        dependencyConfiguration: Action<ExternalModuleDependency>,
    ) = this.copy().apply {
        dependencyConfiguration.execute(this)
        configuration.dependencies.add(this)
    }

    fun ExternalModuleDependency.tee(configuration: Configuration) = this.tee(configuration, {})
}
