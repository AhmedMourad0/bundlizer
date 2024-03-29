Changelog
=========

0.8.0
-----

_2024-02-15_

- Upgraded to Kotlinx-Serialization 1.6.2
- Upgraded to Kotlin 1.9.22
- Upgraded to Android Gradle Plugin 8.2.2
- Upgraded to Gradle 8.2
- Upgraded to Java 19 JVM target

0.7.0
-----

_2022-04-09_

- Added support for passing custom SerializersModule
- Upgraded to Kotlinx-Serialization 1.3.2
- Upgraded to Kotlin 1.6.20
- Upgraded to Android Gradle Plugin 7.1.3
- Upgraded to Gradle 7.4.2

0.6.0
-----

_2021-05-09_

- Upgraded to Kotlinx-Serialization 1.2.0
- Upgraded to Kotlin 1.5.0


0.5.0
-----

_2021-03-04_

- Fixed bug raised in PR (#4) where top-level lists don't encode their size thus producing empty lists when unbundled
- Upgraded to Kotlinx-Serialization runtime 1.1.0
- Upgraded to Kotlin 1.4.31


0.4.0
-----

_2020-12-02_

- Upgraded to Kotlin & Kotlinx.Serialization 1.4.20
- Upgraded to Kotlinx-Serialization runtime 1.0.1


0.3.0
-----

_2020-08-27_

- Migrated to Kotlin & Kotlinx.Serialization 1.4.0
- Migrated to Kotlinx-Serialization runtime 1.0.0-RC


0.2.0
-----

_2020-05-06_

- Fixed bug raised in PR (#1) where empty lists cause a crash when being encoded.
- Improved docs.
- Improved Java support.
- Artifact id changed from bundlizer to bundlizer-core


0.1.0
-----

_2020-05-01_

Initial release!
