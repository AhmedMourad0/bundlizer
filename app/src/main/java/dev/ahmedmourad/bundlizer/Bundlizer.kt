package dev.ahmedmourad.bundlizer

import android.os.Bundle
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy

object Bundlizer {

    fun <T> debundle(deserializer: DeserializationStrategy<T>, bundle: Bundle): T {
        return deserializer.deserialize(BundleDecoder(bundle, true))
    }
    fun <T> bundle(serializer: SerializationStrategy<T>, value: T): Bundle {
        val bundle = Bundle(serializer.descriptor.elementsCount)
        serializer.serialize(BundleEncoder(bundle, null, null, true), value)
        return bundle
    }
}

fun <T> Bundle.debundle(deserializer: DeserializationStrategy<T>): T {
    return Bundlizer.debundle(deserializer, this)
}

fun <T> T.bundle(serializer: SerializationStrategy<T>): Bundle {
    return Bundlizer.bundle(serializer, this)
}
