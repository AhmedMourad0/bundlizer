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
    val id: Int,
    val name: String,
    val isSignedIn: Boolean,
    val email: Email,
    val email1: Email,
    val friends: List<String>,
    val nonFriends: List<String>?,
    val nonFriends2: List<String>? = emptyList(),
    val dept: Map<String, Int>,
    val gender: Gender,
    val birthday: Long,
    val salary: Double
)

@RunWith(AndroidJUnit4::class)
class BundlizerInstrumentedTest {
    @Test
    fun encodedCorrectly() {

        val user = User(
            id = 55,
            name = "Ahmed Mourad",
            isSignedIn = true,
            email = Email("ahmedmourad", "gmail.com"),
            email1 = Email("ahmedmourad1", "gmail.com1"),
            friends = listOf("literally", "everyone", "ever"),
            nonFriends = null,
            dept = mapOf("gov" to 9999, "friend" to 888, "father" to 325),
            gender = Gender.HUMAN,
            birthday = 97637467L,
            salary = 9943.654
        )

        val bundledUser = user.bundle(User.serializer())
        Log.e("dev.ahmedmourad.bundlizer", bundledUser.toString())
        assertEquals(user, bundledUser.unbundle(User.serializer()))
    }
}
