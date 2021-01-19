package tz.co.asoft

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.*
import com.mongodb.client.model.Indexes
import com.mongodb.client.model.Sorts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import org.bson.types.ObjectId

open class MongoDao<T : Entity>(
    override val db: MongoDatabase,
    override val serializer: KSerializer<T>,
    collection: String
) : IMongoDao<T> {

    override var token: String? = null
    override val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

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

    override fun create(list: Collection<T>) = scope.later { list.map { create(it).await() } }

    override fun create(t: T) = scope.later {
        val id = ObjectId.get()
        if (t.uid == null) t.uid = id.toHexString()
        val doc = t.toDocument(serializer).apply {
            append("_id", id)
        }
        collection.insertOne(doc)
        doc.to(serializer)
    }

    override fun edit(list: Collection<T>) = scope.later { list.map { edit(it).await() } }

    override fun edit(t: T) = scope.later {
        val doc = t.toDocument(serializer)
        collection.replaceOne(eq("uid", t.uid), doc)
        doc.to(serializer)
    }

    override fun delete(list: Collection<T>): Later<List<T>> = scope.later {
        list.map {
            it.deleted = true
            edit(it).await()
        }
    }

    override fun delete(t: T) = scope.later { delete(listOf(t)).await().first() }

    override fun wipe(list: Collection<T>) = scope.later { list.map { wipe(it).await() } }

    override fun wipe(t: T) = scope.later {
        collection.deleteOne(eq("uid", t.uid))
        t
    }

    override fun load(uids: Collection<String>) = scope.later { uids.mapNotNull { load(it).await() } }

    override fun load(uid: String) = scope.later {
        val doc = collection.find(eq("uid", uid)).first()
        doc?.to(serializer)
    }

    override fun page(no: Int, size: Int) = scope.later {
        require(no > 0) { "Page numbering starts from one" }
        val filters = eq("deleted", false)
        val sorts = Sorts.ascending("uid")
        val skips = (no - 1) * size
        collection.find(filters).sort(sorts).skip(skips).limit(size).mapNotNull { it.to(serializer) }
    }

    override fun all() = scope.later {
        val doc = collection.find(eq("deleted", false))
        doc.mapNotNull { it.to(serializer) }
    }

    override fun allDeleted() = scope.later {
        val doc = collection.find(eq("deleted", true))
        doc.mapNotNull { it.to(serializer) }
    }
}