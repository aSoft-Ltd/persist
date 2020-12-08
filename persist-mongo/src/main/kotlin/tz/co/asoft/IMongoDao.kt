package tz.co.asoft

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import kotlinx.serialization.KSerializer
import org.bson.Document

interface IMongoDao<T : Entity> : IDao<T> {
    val serializer: KSerializer<T>
    val db: MongoDatabase
    val collection: MongoCollection<Document>
}