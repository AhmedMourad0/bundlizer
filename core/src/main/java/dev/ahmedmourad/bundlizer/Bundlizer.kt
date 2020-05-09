package dev.ahmedmourad.bundlizer

import android.os.Bundle
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy

/**
 * Utility class for the Bundlizer library
 */
object Bundlizer {

    /**
     * Deserialize this bundle into an object of type T
     *
     * @param deserializer DeserializationStrategy of the T class
     * @param bundle bundle to deserialize
     * @return object of type T deserialized from bundle
     */
    @JvmStatic
    fun <T> unbundle(deserializer: DeserializationStrategy<T>, bundle: Bundle): T {
        return deserializer.deserialize(BundleDecoder(bundle, -1, true))
    }

    /**
     * Serialize this object into a bundle
     *
     * @param serializer SerializationStrategy of the T class
     * @param value value to serialize
     * @return bundle serialized from value
     */
    @JvmStatic
    fun <T> bundle(serializer: SerializationStrategy<T>, value: T): Bundle {
        val bundle = Bundle(serializer.descriptor.elementsCount)
        serializer.serialize(BundleEncoder(bundle, null, null, true), value)
        return bundle
    }
}

/**
 * Deserialize this bundle into an object of type T
 *
 * @receiver bundle to deserialize
 * @param deserializer DeserializationStrategy of the T class
 * @return object of type T deserialized from bundle
 */
fun <T> Bundle.unbundle(deserializer: DeserializationStrategy<T>): T {
    return Bundlizer.unbundle(deserializer, this)
}

/**
 * Serialize this object into a bundle
 *
 * @receiver value to serialize
 * @param serializer SerializationStrategy of the T class
 * @return bundle serialized from value
 */
fun <T> T.bundle(serializer: SerializationStrategy<T>): Bundle {
    return Bundlizer.bundle(serializer, this)
}
