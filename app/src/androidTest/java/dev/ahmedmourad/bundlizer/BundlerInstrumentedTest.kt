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
    val dept: Map<String, Int>,
    val gender: Gender,
    val birthday: Long,
    val salary: Double
)

@RunWith(AndroidJUnit4::class)
class BundlerInstrumentedTest {
    @Test
    fun encodedCorrectly() {

        val user = User(
            55,
            "Ahmed Mourad",
            true,
            Email("ahmedmourad", "gmail.com"),
            Email("ahmedmourad1", "gmail.com1"),
            listOf("literally", "everyone", "ever"),
            null,
            mapOf("gov" to 9999, "friend" to 888, "father" to 325),
            Gender.HUMAN,
            97637467L,
            9943.654
        )

        val bundledUser = user.bundle(User.serializer())
        Log.e("dev.ahmedmourad.bundlizer", bundledUser.toString())
        assertEquals(user, bundledUser.unbundle(User.serializer()))
    }
}
