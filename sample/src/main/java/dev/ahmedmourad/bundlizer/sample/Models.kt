package dev.ahmedmourad.bundlizer.sample

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

enum class Gender {
    MONKEY, HUMAN
}

data class Child(val s: String) : Serializable

data class Parent(val c: Child) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readSerializable() as Child)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(c)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Parent> {
        override fun createFromParcel(parcel: Parcel): Parent {
            return Parent(parcel)
        }

        override fun newArray(size: Int): Array<Parent?> {
            return arrayOfNulls(size)
        }
    }
}
