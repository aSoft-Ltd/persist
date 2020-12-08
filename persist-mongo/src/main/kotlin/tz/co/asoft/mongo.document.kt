package tz.co.asoft

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import org.bson.Document

fun <T : Entity> T.toDocument(serializer: SerializationStrategy<T>): Document {
    return Document.parse(Json.encodeToString(serializer, this))
}

fun <T : Entity> Document.to(serializer: DeserializationStrategy<T>): T = Json.decodeFromString(serializer, toJson())

fun <T : Entity> Collection<Document>.to(serializer: DeserializationStrategy<T>) = map { it.to(serializer) }

fun <T : Entity> Collection<T>.toDocuments(deserializer: SerializationStrategy<T>) = map { it.toDocument(deserializer) }