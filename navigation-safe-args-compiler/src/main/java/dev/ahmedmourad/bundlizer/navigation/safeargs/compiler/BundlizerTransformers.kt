package dev.ahmedmourad.bundlizer.navigation.safeargs.compiler

import arrow.meta.CliPlugin
import arrow.meta.Meta
import arrow.meta.invoke
import arrow.meta.quotes.Transform
import arrow.meta.quotes.namedFunction
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.containingClass

const val FQ_NAME_NAV_ARGS = "androidx.navigation.NavArgs"
const val NAME_KOTLINX_SERIALIZABLE = "kotlinx.serialization.Serializable"

val Meta.toBundleTransformer: CliPlugin
    get() = "toBundleTransformer" {
        meta(namedFunction({
            name == "toBundle" && this.containingClass()?.hasSuperType(FQ_NAME_NAV_ARGS) == true
        }) { c ->
            Transform.replace(
                replacing = c,
                newDeclaration = """
                    fun toBundle(): Bundle {
                    |   val result = Bundle()
                    |   if (Parcelable::class.java.isAssignableFrom(Bundle::class.java)) {
                    |       result.putParcelable("child", this.child as Parcelable?)
                    |   } else if (Serializable::class.java.isAssignableFrom(Bundle::class.java)) {
                    |       result.putSerializable("child", this.child as Serializable?)
                    |   } else if (Bundle::class.java.annotations.any { it.annotationClass.qualifiedName == "$NAME_KOTLINX_SERIALIZABLE" }) {
                    |           result.putBundle("child", this.child.bundle(Child.serializer()))
                    |   } else {
                    |       throw UnsupportedOperationException(Bundle::class.java.name +
                    |           " must implement Parcelable or Serializable or must be an Enum.")
                    |   }
                    |   return result
                    |}
                """.function
            )
        })
    }

private fun KtClass.hasSuperType(fqName: String): Boolean {
    return this.superTypeListEntries.any { it.name == FQ_NAME_NAV_ARGS }
}
