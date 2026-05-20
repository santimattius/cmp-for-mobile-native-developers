package com.santimattius.kmp.skeleton.navigation

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

/**
 * Validates that the production Destination sealed interface serializes and deserializes
 * correctly, proving the approach is safe for both JVM and Kotlin/Native (iOS).
 *
 * Phase 3.4 — Updated from spike types (SpikeDestination et al.) to real production types.
 */
class DestinationSerializationTest {

    @Test
    fun splashSerializesAndDeserializes() {
        val json = Json
        val encoded = json.encodeToString<Destination>(Splash)
        val decoded = json.decodeFromString<Destination>(encoded)
        assertIs<Splash>(decoded)
    }

    @Test
    fun homeSerializesAndDeserializes() {
        val json = Json
        val encoded = json.encodeToString<Destination>(Home)
        val decoded = json.decodeFromString<Destination>(encoded)
        assertIs<Home>(decoded)
    }

    @Test
    fun characterDetailSerializesAndDeserializes() {
        val original = CharacterDetail(id = "42")
        val json = Json
        val encoded = json.encodeToString<Destination>(original)
        val decoded = json.decodeFromString<Destination>(encoded)
        assertIs<CharacterDetail>(decoded)
        assertEquals("42", decoded.id)
    }

    @Test
    fun repositorySerializesAndDeserializes() {
        val json = Json
        val encoded = json.encodeToString<Destination>(Repository)
        val decoded = json.decodeFromString<Destination>(encoded)
        assertIs<Repository>(decoded)
    }

    @Test
    fun polymorphicDestinationRoundTrip() {
        // Critical test: proves sealed subtype dispatch works via explicit SerializersModule.
        // This is the safe approach for both JVM and Kotlin/Native (iOS),
        // because it avoids relying on reflection-based subclassesOfSealed<T>().
        val json = Json {
            serializersModule = SerializersModule {
                polymorphic(Destination::class) {
                    subclass(Splash::class)
                    subclass(Home::class)
                    subclass(Favorites::class)
                    subclass(CharacterDetail::class)
                    subclass(Repository::class)
                }
            }
        }

        val destinations: List<Destination> = listOf(
            Splash,
            Home,
            Favorites,
            CharacterDetail(id = "99"),
            Repository,
        )

        destinations.forEach { destination ->
            val encoded = json.encodeToString(Destination.serializer(), destination)
            val decoded = json.decodeFromString(Destination.serializer(), encoded)
            assertEquals(destination, decoded)
        }
    }
}
