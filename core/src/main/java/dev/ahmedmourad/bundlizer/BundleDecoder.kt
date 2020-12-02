package dev.ahmedmourad.bundlizer

import android.os.Bundle
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

@ExperimentalSerializationApi
internal class BundleDecoder(
    private val bundle: Bundle,
    private val elementsCount: Int = -1,
    private val isInitializer: Boolean = true
) : AbstractDecoder() {

    override val serializersModule: SerializersModule = EmptySerializersModule

    private var index = -1
    private var key: String = ""

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {

        if (++index >= elementsCount)
            return CompositeDecoder.DECODE_DONE

        key = descriptor.getElementName(index)
        return index
    }

    override fun beginStructure(
        descriptor: SerialDescriptor
    ): CompositeDecoder {

        val b = if (isInitializer) {
            bundle
        } else {
            bundle.getBundle(key)!!
        }

        val count = when (descriptor.kind) {
            StructureKind.MAP, StructureKind.LIST -> b.getInt("\$size")
            else -> descriptor.elementsCount
        }

        return BundleDecoder(
            bundle = b,
            elementsCount = count,
            isInitializer = false
        )
    }

    override fun endStructure(descriptor: SerialDescriptor) {

    }

    override fun decodeBoolean(): Boolean {
        return bundle.getBoolean(key)
    }

    override fun decodeByte(): Byte {
        return bundle.getByte(key)
    }

    override fun decodeChar(): Char {
        return bundle.getChar(key)
    }

    override fun decodeDouble(): Double {
        return bundle.getDouble(key)
    }

    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
        return bundle.getInt(key)
    }

    override fun decodeFloat(): Float {
        return bundle.getFloat(key)
    }

    override fun decodeInt(): Int {
        return bundle.getInt(key)
    }

    override fun decodeLong(): Long {
        return bundle.getLong(key)
    }

    override fun decodeNotNullMark(): Boolean {
        return bundle.containsKey(key)
    }

    override fun decodeNull(): Nothing? {
        return null
    }

    override fun decodeShort(): Short {
        return bundle.getShort(key)
    }

    override fun decodeString(): String {
        return bundle.getString(key)!!
    }

    override fun decodeValue(): Any {
        return super.decodeValue()
    }
}
