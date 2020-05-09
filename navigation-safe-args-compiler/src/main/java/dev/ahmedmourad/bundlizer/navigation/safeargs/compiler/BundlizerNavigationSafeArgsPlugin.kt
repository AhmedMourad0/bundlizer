package dev.ahmedmourad.bundlizer.navigation.safeargs.compiler

import arrow.meta.CliPlugin
import arrow.meta.Meta
import arrow.meta.phases.CompilerContext
import kotlin.contracts.ExperimentalContracts

class BundlizerNavigationSafeArgsPlugin : Meta {
    @ExperimentalContracts
    override fun intercept(ctx: CompilerContext): List<CliPlugin> = listOf(
//        toBundleTransformer
    )
}
