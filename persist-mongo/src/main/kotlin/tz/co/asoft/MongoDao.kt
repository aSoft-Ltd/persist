package tz.co.asoft

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.*
import com.mongodb.client.model.Indexes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import org.bson.types.ObjectId

open class MongoDao<T : Entity>(
    override val db: MongoDatabase,
    override val serializer: KSerializer<T>,
    collection: String
) : IMongoDao<T> {

    constructor(
        options: MongoOptions,
        client: MongoClient = options.toClient(),
        db: MongoDatabase = client.getDatabase(options.database),
        serializer: KSerializer<T>,
        collection: String
    ) : this(client, options.database, db, serializer, collection)

    constructor(
        client: MongoClient,
        dbName: String,
        db: MongoDatabase = client.getDatabase(dbName),
        serializer: KSerializer<T>,
        collection: String
    ) : this(db, serializer, collection)

    override val collection = db.getCollection(collection).apply {
        createIndex(Indexes.ascending("uid"))
    }

    override suspend fun create(list: Collection<T>) = list.map { create(it) }

    override suspend fun create(t: T) = withContext(Dispatchers.IO) {
        val id = ObjectId.get()
        if (t.uid == null) t.uid = id.toHexString()
        val doc = t.toDocument(serializer).apply {
            append("_id", id)
        }
        collection.insertOne(doc)
        doc.to(serializer)
    }

    override suspend fun edit(list: Collection<T>) = list.map { edit(it) }

    override suspend fun edit(t: T) = withContext(Dispatchers.IO) {
        val doc = t.toDocument(serializer)
        collection.replaceOne(eq("uid", t.uid), doc)
        doc.to(serializer)
    }

    override suspend fun delete(list: Collection<T>) = list.map {
        it.deleted = true
        edit(it)
    }

    override suspend fun delete(t: T): T = delete(listOf(t)).first()

    override suspend fun wipe(list: Collection<T>) = list.map { wipe(it) }

    override suspend fun wipe(t: T) = withContext(Dispatchers.IO) {
        collection.deleteOne(eq("uid", t.uid))
        t
    }

    override suspend fun load(uids: Collection<String>) = uids.mapNotNull { load(it.toString()) }

    override suspend fun load(uid: String) = withContext(Dispatchers.IO) {
        val doc = collection.find(eq("uid", uid)).first()
        doc?.to(serializer)
    }

    override suspend fun all() = withContext(Dispatchers.IO) {
        val doc = collection.find(eq("deleted", false))
        doc.mapNotNull { it.to(serializer) }
    }

    override suspend fun allDeleted() = withContext(Dispatchers.IO) {
        val doc = collection.find(eq("deleted", true))
        doc.mapNotNull { it.to(serializer) }
    }
}