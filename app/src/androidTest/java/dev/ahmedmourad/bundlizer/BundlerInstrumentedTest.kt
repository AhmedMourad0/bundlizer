package dev.ahmedmourad.bundlizer

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.serialization.Serializable

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

enum class Gender {
    HUMAN, MONKEY
}

@Serializable
data class Email(val id:String, val domain: String)

@Serializable
data class User(
    val id: Int,
    val name: String,
    val isSignedIn: Boolean,
    val email: Email,
    val email1: Email,
    val friends: List<String>?,
    val nonFriends: List<String>,
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
            Email("dev.ahmedmourad", "gmail.com"),
            Email("dev.ahmedmourad1", "gmail.com1"),
            null,
            listOf("literally", "everyone", "ever"),
            Gender.HUMAN,
            97637467L,
            9943.654
        )

        val bundledUser = user.bundle(User.serializer())
        Log.e("dev.ahmedmourad.Bundliz", bundledUser.toString())
        assertEquals(user, bundledUser.debundle(User.serializer()))
    }
}
