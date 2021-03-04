Bundlizer  ![CI](https://github.com/AhmedMourad0/bundlizer/workflows/CI/badge.svg) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.ahmedmourad.bundlizer/bundlizer-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.ahmedmourad.bundlizer/bundlizer-core)
===================================================================================

Android Bundle format support for Kotlinx Serialization.

## Usage

Annotate your data models with `@Serializable`:

```kotlin
import kotlinx.serialization.Serializable

enum class Type {
    HUMAN, MONKEY
}

@Serializable
data class Email(val value: String)

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: Email,
    val friends: List<String>?,
    val something: Map<String, Int>,
    val type: Type
)
```

### To Bundle

Use `Bundlizer.bundle` or `bundle` extension function:

```kotlin
import dev.ahmedmourad.bundlizer.Bundlizer
import dev.ahmedmourad.bundlizer.bundle
import android.os.Bundle

val user: User = ...

val bundle: Bundle = Bundlizer.bundle(User.serializer(), user)
// or
val bundle: Bundle = user.bundle(User.serializer())
```

### From Bundle
Use `Bundlizer.unbundle` or `unbundle` extension function:

```kotlin
import dev.ahmedmourad.bundlizer.Bundlizer
import dev.ahmedmourad.bundlizer.unbundle
import android.os.Bundle

val bundle: Bundle = ...

val user: User = Bundlizer.unbundle(User.serializer(), bundle)
// or
val user: User = bundle.unbundle(User.serializer())
```

## Installation

- Add [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) to your project.

- In your module-level `build.gradle`:
```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation "dev.ahmedmourad.bundlizer:bundlizer-core:0.5.0"
}
```

License
-------

    Copyright (C) 2020 Ahmed Mourad

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 [snapshots]: https://oss.sonatype.org/content/repositories/snapshots/
 
