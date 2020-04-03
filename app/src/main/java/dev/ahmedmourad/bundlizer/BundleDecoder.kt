package dev.ahmedmourad.bundlizer

import android.os.Bundle
import android.util.Log
import kotlinx.serialization.*
import kotlinx.serialization.builtins.AbstractDecoder

internal class BundleDecoder(
    private val bundle: Bundle,
    private val isInitializer: Boolean = true
) : AbstractDecoder() {

    private var index = -1
    private var key: String = ""

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {

        Log.e("dghdfgsf", "$key   ${descriptor.elementNames()}")
        if (++index >= descriptor.elementsCount)
            return -1

        key = descriptor.getElementName(index)
        return index
    }

    override fun beginStructure(
        descriptor: SerialDescriptor,
        vararg typeParams: KSerializer<*>
    ): CompositeDecoder {
        Log.e("dghdfgsf", key)
        return if (isInitializer)  {
            BundleDecoder(
                bundle,
                false
            )
        } else {
            BundleDecoder(
                bundle.getBundle(key)!!,
                false
            )
        }
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

    override fun decodeUnit() {

    }

    override fun decodeValue(): Any {
        return super.decodeValue()
    }
}
