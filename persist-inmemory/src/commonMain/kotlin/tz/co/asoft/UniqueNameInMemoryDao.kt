package tz.co.asoft

open class UniqueNameInMemoryDao<T : NamedEntity>(prefix: String) : InMemoryDao<T>(prefix) {
    override fun create(t: T) = scope.later {
        if (all().await().any { it.name == t.name }) {
            throw Exception("Entity with name ${t.name} already exists")
        }
        super.create(t).await()
    }

    override fun edit(t: T) = scope.later {
        if (all().await().any { it.name == t.name && it.uid != t.uid }) {
            throw Exception("Entity with name ${t.name} already exists")
        }
        super.edit(t).await()
    }
}