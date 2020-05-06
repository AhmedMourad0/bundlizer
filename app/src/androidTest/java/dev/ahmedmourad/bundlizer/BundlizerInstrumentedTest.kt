package dev.ahmedmourad.bundlizer

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.serialization.Serializable
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

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

@RunWith(AndroidJUnit4::class)
class BundlizerInstrumentedTest {
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
}
