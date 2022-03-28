package dev.ahmedmourad.bundlizer

import android.os.Build
import android.util.Log
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

enum class Gender {
    HUMAN, MONKEY
}

@Serializable
data class SmallDataClass(val stringTest: String, val intTest: Int)

@Serializable
data class BigDataClass(
    val intTest: Int,
    val stringTest: String,
    val booleanTrueTest: Boolean,
    val booleanFalseTest: Boolean,
    val dataClassTest: SmallDataClass,
    val nullableDataClassTest: SmallDataClass?,
    val nullableOptionalDataClassTest: SmallDataClass? = null,
    val listTest: List<String>,
    val listOfDataClassesTest: List<String>,
    val nullableListTest: List<String>?,
    val optionalListTest: List<String> = listOf("nullableOptionalListTest - 1"),
    val optionalEmptyListTest: List<String> = emptyList(),
    val nullableOptionalListTest: List<String>? = listOf("nullableOptionalListTest - 1"),
    val nullableOptionalEmptyListTest: List<String>? = emptyList(),
    val nullOptionalListTest: List<String>? = null,
    val mapTest: Map<String, Int>,
    val mapOfDataClassesTest: Map<String, SmallDataClass>,
    val nullableMapTest: Map<String, String>?,
    val optionalMapTest: Map<String, String> = mapOf("optionalMapTest - k1" to "optionalMapTest - v1"),
    val optionalEmptyMapTest: Map<String, String> = emptyMap(),
    val nullableOptionalMapTest: Map<String, String>? = mapOf("nullableOptionalMapTest - k1" to "nullableOptionalMapTest - v1"),
    val nullableOptionalEmptyMapTest: Map<String, String>? = emptyMap(),
    val nullOptionalMapTest: Map<String, String>? = null,
    val enumTest: Gender,
    val differentEnumTest: Gender,
    val longTest: Long,
    val doubleTest: Double,
    val floatTest: Float
)

@Serializable
data class DataClassWithPolymorphicMap(
    val polymorphicMapTest: Map<String, @Polymorphic Any> = mapOf(
        "Case_1" to PolymorphicCaseOne(),
        "Case_2" to PolymorphicCaseTwo(),
    )
)

@Serializable
data class PolymorphicCaseOne(val data: String = "some data 1")

@Serializable
data class PolymorphicCaseTwo(val data: String = "some data 2")

private val TestSerializersModule = SerializersModule {
    polymorphic(Any::class) {
        subclass(PolymorphicCaseOne::class)
        subclass(PolymorphicCaseTwo::class)
    }
}


@Config(sdk = [Build.VERSION_CODES.O_MR1]) //TODO: remove this when you upgrade robolectric to 4.3.1
@RunWith(RobolectricTestRunner::class)
class BundlizerCoreUnitTest {

    @Test
    fun flat() {
        val value = 42
        val serializer = Int.serializer()
        val unbundled = value.bundle(serializer).unbundle(serializer)
        assertEquals(value, unbundled)
    }

    @Test
    fun flatNullable() {
        val value: Int? = null
        val serializer = Int.serializer().nullable
        val unbundled: Int? = value.bundle(serializer).unbundle(serializer)
        assertEquals(value, unbundled)
    }

    @Test
    fun data_is_encoded_and_then_decoded_correctly() {

        val user = BigDataClass(
            intTest = 55,
            stringTest = "stringTest - v",
            booleanTrueTest = true,
            booleanFalseTest = false,
            dataClassTest = SmallDataClass("dataClassTest - k1", 1),
            nullableDataClassTest = SmallDataClass("nullableDataClassTest - k1", 1),
            listTest = listOf("listTest - e1", "listTest - e2"),
            listOfDataClassesTest = listOf(
                "listOfDataClassesTest - e1",
                "listOfDataClassesTest - e2"
            ),
            nullableListTest = listOf("nullableListTest - e1", "nullableListTest - e2"),
            mapTest = mapOf("mapTest - k1" to 1, "mapTest - k2" to 2),
            mapOfDataClassesTest = mapOf(
                "mapOfDataClassesTest - k1" to SmallDataClass("v1 - 1", 12),
                "mapOfDataClassesTest - k2" to SmallDataClass("v2 - 1", 22)
            ),
            nullableMapTest = mapOf("nullableMapTest - k1" to "nullableMapTest - k2"),
            enumTest = Gender.HUMAN,
            differentEnumTest = Gender.MONKEY,
            longTest = 97637467L,
            doubleTest = 9943.654,
            floatTest = 9934543.65534f
        )

        val bundledUser = user.bundle(BigDataClass.serializer())
        Log.e("dev.ahmedmourad.bundlizer", bundledUser.toString())
        assertEquals(user, bundledUser.unbundle(BigDataClass.serializer()))
    }

    @Test
    fun lists_are_encoded_and_then_decoded_correctly() {
        repeat(10) { size ->
            val items = List(size) {
                UUID.randomUUID().toString()
            }
            val bundledList = items.bundle(ListSerializer(String.serializer()))
            Log.e("dev.ahmedmourad.bundlizer", bundledList.toString())
            assertEquals(items, bundledList.unbundle(ListSerializer(String.serializer())))
        }
    }

    @Test
    fun maps_are_encoded_and_then_decoded_correctly() {
        repeat(10) { size ->
            val items = List(size) {
                UUID.randomUUID().toString() to UUID.randomUUID().toString()
            }.toMap()
            val bundledList = items.bundle(MapSerializer(String.serializer(), String.serializer()))
            Log.e("dev.ahmedmourad.bundlizer", bundledList.toString())
            assertEquals(
                items,
                bundledList.unbundle(MapSerializer(String.serializer(), String.serializer()))
            )
        }
    }

    @Test
    fun polymorphic_types_encoded_and_then_decoded_correctly() {
        val state = DataClassWithPolymorphicMap()
        Bundlizer.defaultSerializersModule = TestSerializersModule
        val bundledState = state.bundle(DataClassWithPolymorphicMap.serializer())
        Log.e("dev.ahmedmourad.bundlizer", bundledState.toString())
        assertEquals(
            state,
            bundledState.unbundle(DataClassWithPolymorphicMap.serializer())
        )
    }
}
