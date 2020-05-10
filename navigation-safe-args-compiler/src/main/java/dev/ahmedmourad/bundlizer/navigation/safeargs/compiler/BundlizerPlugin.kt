package dev.ahmedmourad.bundlizer.navigation.safeargs.compiler

import arrow.meta.CliPlugin
import arrow.meta.Meta
import arrow.meta.phases.CompilerContext
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.config.CompilerConfiguration

class BundlizerPlugin : Meta {

    override fun intercept(ctx: CompilerContext): List<CliPlugin> = listOf(
//        toBundle
    )

    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration
    ) {
        super.registerProjectComponents(project, configuration)
    }
}
