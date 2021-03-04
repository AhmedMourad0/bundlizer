package dev.ahmedmourad.bundlizer

import android.os.Bundle
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

@ExperimentalSerializationApi
internal class BundleEncoder(
    private val bundle: Bundle,
    private val parentBundle: Bundle? = null,
    private val keyInParent: String? = null,
    private val isInitializer: Boolean = true
) : AbstractEncoder() {

    override val serializersModule: SerializersModule = EmptySerializersModule

    private var key: String = ""

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        this.key = descriptor.getElementName(index)
        return super.encodeElement(descriptor, index)
    }

    override fun beginStructure(
        descriptor: SerialDescriptor
    ): CompositeEncoder {
        return if (isInitializer) {
            BundleEncoder(
                bundle = bundle,
                parentBundle = null,
                keyInParent = key,
                isInitializer = false
            )
        } else {
            BundleEncoder(
                bundle = Bundle(),
                parentBundle = bundle,
                keyInParent = key,
                isInitializer = false
            )
        }
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        
        if (descriptor.kind in arrayOf(StructureKind.LIST, StructureKind.MAP)) {
            val size = key.toIntOrNull()?.let { it + 1 } ?: 0
            bundle.putInt("\$size", size)
        }
        
        if (keyInParent.isNullOrBlank()) {
            return
        }

        parentBundle?.putBundle(keyInParent, bundle)
    }

    override fun encodeBoolean(value: Boolean) {
        bundle.putBoolean(key, value)
    }

    override fun encodeByte(value: Byte) {
        bundle.putByte(key, value)
    }

    override fun encodeChar(value: Char) {
        bundle.putChar(key, value)
    }

    override fun encodeDouble(value: Double) {
        bundle.putDouble(key, value)
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        bundle.putInt(key, index)
    }

    override fun encodeFloat(value: Float) {
        bundle.putFloat(key, value)
    }

    override fun encodeInt(value: Int) {
        bundle.putInt(key, value)
    }

    override fun encodeLong(value: Long) {
        bundle.putLong(key, value)
    }

    override fun encodeNull() {

    }

    override fun encodeShort(value: Short) {
        bundle.putShort(key, value)
    }

    override fun encodeString(value: String) {
        bundle.putString(key, value)
    }

    override fun encodeNotNullMark() {

    }
}
