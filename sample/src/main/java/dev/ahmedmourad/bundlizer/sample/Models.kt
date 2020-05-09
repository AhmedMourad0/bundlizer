package dev.ahmedmourad.bundlizer.sample

import kotlinx.serialization.Serializable

@Serializable
data class Child(val s: String)

@Serializable
data class Parent(val c: Child)
