package dev.ahmedmourad.bundlizer.navigation.safeargs.compiler

import arrow.meta.CliPlugin
import arrow.meta.Meta
import arrow.meta.invoke
import arrow.meta.quotes.Transform
import arrow.meta.quotes.classBody
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.containingClass

private const val FQ_NAME_NAV_ARGS = "androidx.navigation.NavArgs"
private const val KOTLINX_SERIALIZABLE = "kotlinx.serialization.Serializable"

private val PRIMITIVES_AND_PRIMITIVES_ARRAYS = arrayOf(
    "Int",
    "IntArray",
    "Float",
    "FloatArray",
    "Double",
    "DoubleArray",
    "Long",
    "LongArray",
    "String",
    "StringArray"
)

internal val Meta.toBundle: CliPlugin
    get() = "toBundleTransformer" {
        meta(classBody({
            this.containingClass()?.hasSuperType(FQ_NAME_NAV_ARGS) == true
        }) { _ ->
            val functionToTransform = this.functions.value.first { it.name == "toBundle" }
            val properties = this.properties.value.map(KtProperty::toSimpleProperty)
            val rootBundleName = "result"
            Transform.replace(
                replacing = functionToTransform,
                newDeclaration = """
                   |fun toBundle(): Bundle {
                   |    val $rootBundleName = Bundle()
                   |    ${properties.generateBundleConstructionBody(rootBundleName)}
                   |    return $rootBundleName
                   |}
                """.trimIndent().function
            )
        })
    }

private fun List<SimpleProperty>.generateBundleConstructionBody(rootBundleName: String): String {
    return this.joinToString("\n\n") { property ->
        when {

            property.type in PRIMITIVES_AND_PRIMITIVES_ARRAYS -> property.generatePrimitiveOrPrimitiveArrayBody(
                rootBundleName
            )

            property.type.startsWith("Array<") -> TODO()

            else -> property.generateCustomDataTypeBody(rootBundleName)
        }
    }
}

private fun SimpleProperty.generatePrimitiveOrPrimitiveArrayBody(rootBundleName: String): String {
    return """
       |$rootBundleName.put$type("$name", this.$name)
    """.trimIndent()
}

private fun SimpleProperty.generateCustomDataTypeBody(rootBundleName: String): String {
    val typeNullability = typeNullability(isNullable)
    val serializerNullability = serializerNullability(isNullable)
    return """
       |if (Parcelable::class.java.isAssignableFrom($type::class.java)) {
       |    $rootBundleName.putParcelable("$name", this.$name as Parcelable$typeNullability)
       |} else if (Serializable::class.java.isAssignableFrom(Bundle::class.java)) {
       |    $rootBundleName.putSerializable("$name", this.$name as Serializable$typeNullability)
       |} else if ($type::class.java.annotations.any { it.annotationClass.qualifiedName == "$KOTLINX_SERIALIZABLE" }) {
       |    $rootBundleName.putBundle("$name", this.$name.bundle($type.serializer()$serializerNullability))
       |} else {
       |    throw UnsupportedOperationException($type::class.java.name +
       |        " must implement Parcelable or Serializable, be annotated with"
       |        " $KOTLINX_SERIALIZABLE or must be an Enum."
       |    )
       |}
    """.trimIndent()
}

private fun typeNullability(isNullable: Boolean): String {
    return if (isNullable) "?" else ""
}

private fun serializerNullability(isNullable: Boolean): String {
    return if (isNullable) ".nullable" else ""
}

private fun KtClass.hasSuperType(fqName: String): Boolean {
    return this.superTypeListEntries.any { it.name == fqName }
}

private data class SimpleProperty(
    val name: String,
    val type: String,
    val isNullable: Boolean
)

private fun KtProperty.toSimpleProperty(): SimpleProperty {
    val typeInSource = this.typeReference!!.text
    val isNullable = typeInSource.endsWith("?")
    val typeFqName = if (isNullable) {
        typeInSource.dropLast(1)
    } else {
        typeInSource
    }
    return SimpleProperty(this.name!!, typeFqName, isNullable)
}
