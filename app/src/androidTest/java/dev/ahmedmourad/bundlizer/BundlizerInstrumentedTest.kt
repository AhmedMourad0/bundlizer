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
data class Email(val id: String, val domain: String)

@Serializable
data class User(
    val intTest: Int,
    val stringTest: String,
    val booleanTest: Boolean,
    val dataClassTest: Email,
    val nullableDataClassTest: Email?,
    val nullableOptionalDataClassTest: Email? = null,
    val listTest: List<String>,
    val listOfDataClassesTest: List<String>,
    val nullableListTest: List<String>?,
    val optionalListTest: List<String> = listOf("nullableOptionalListTest - 1"),
    val optionalEmptyListTest: List<String> = emptyList(),
    val nullableOptionalListTest: List<String>? = listOf("nullableOptionalListTest - 1"),
    val nullableOptionalEmptyListTest: List<String>? = emptyList(),
    val nullOptionalListTest: List<String>? = null,
    val mapTest: Map<String, Int>,
    val mapOfDataClassesTest: Map<String, Email>,
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
    fun encodedCorrectly() {

        val user = User(
            intTest = 55,
            stringTest = "Ahmed Mourad",
            booleanTest = true,
            dataClassTest = Email("ahmedmourad", "gmail.com"),
            nullableDataClassTest = Email("ahmedmourad1", "gmail.com1"),
            listTest = listOf("listTest - e1", "listTest - e2"),
            listOfDataClassesTest = listOf(
                "listOfDataClassesTest - e1",
                "listOfDataClassesTest - e2"
            ),
            nullableListTest = listOf("nullableListTest - e1", "nullableListTest - e2"),
            mapTest = mapOf("mapTest - k1" to 1, "mapTest - k2" to 2),
            mapOfDataClassesTest = mapOf(
                "mapOfDataClassesTest - k1" to Email("v1 - 1", "v1 - 2"),
                "mapOfDataClassesTest - k2" to Email("v2 - 1", "v2 - 2")
            ),
            nullableMapTest = mapOf("nullableMapTest - k1" to "nullableMapTest - k2"),
            enumTest = Gender.HUMAN,
            differentEnumTest = Gender.MONKEY,
            longTest = 97637467L,
            doubleTest = 9943.654,
            floatTest = 9934543.65534f
        )

        val bundledUser = user.bundle(User.serializer())
        Log.e("dev.ahmedmourad.bundlizer", bundledUser.toString())
        assertEquals(user, bundledUser.unbundle(User.serializer()))
    }
}
