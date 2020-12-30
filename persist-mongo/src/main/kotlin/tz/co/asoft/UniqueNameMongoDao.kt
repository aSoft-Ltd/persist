package tz.co.asoft

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import kotlinx.serialization.KSerializer

open class UniqueNameMongoDao<T : NamedEntity>(
    override val db: MongoDatabase,
    override val serializer: KSerializer<T>,
    collection: String
) : MongoDao<T>(db, serializer, collection) {

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

    override fun create(t: T) = scope.later {
        if (!collection.find(eq("name", t.name)).none()) {
            throw Exception("Entity with name: ${t.name} already exist in the database")
        }
        super.create(t).await()
    }

    override fun edit(t: T) = scope.later {
        val found = collection.find(eq("name", t.name)).any {
            it.getString("name") == t.name && it.getString("uid") != t.uid
        }

        if (found) throw Exception("Entity with name: ${t.name} already exist in the database")

        super.edit(t).await()
    }
}