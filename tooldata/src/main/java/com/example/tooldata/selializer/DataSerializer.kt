package com.example.tooldata.selializer

import androidx.datastore.core.Serializer
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.KClass


internal class DataSerializer<T : Any>(
	override val defaultValue: T,
	private val typeOf: KClass<T>
) : BaseSerializer<T>() {

	@OptIn(InternalSerializationApi::class)
	override val serializer: KSerializer<T>
		get() = typeOf.serializer()
}

internal abstract class BaseSerializer<T> : Serializer<T> {

	abstract val serializer: KSerializer<T>

	override suspend fun readFrom(input: InputStream): T {
		val string = input.bufferedReader().use { it.readText() }
		return try {
			Json.decodeFromString(
				deserializer = serializer,
				string = string
			)
		} catch (e: SerializationException) {
			e.printStackTrace()
			defaultValue
		}
	}

	override suspend fun writeTo(t: T, output: OutputStream) {
		val bytes = Json.encodeToString(
			serializer = serializer,
			value = t
		).encodeToByteArray()
		output.use {
			it.write(bytes)
		}
	}
}