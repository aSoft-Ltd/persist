package tz.co.asoft

open class UniqueNameInMemoryDao<T : NamedEntity>(prefix: String) : InMemoryDao<T>(prefix) {
    override suspend fun create(t: T): T {
        if (all().any { it.name == t.name }) {
            throw Exception("Entity with name ${t.name} already exists")
        }
        return super.create(t)
    }

    override suspend fun edit(t: T): T {
        if (all().any { it.name == t.name && it.uid != t.uid }) {
            throw Exception("Entity with name ${t.name} already exists")
        }
        return super.edit(t)
    }
}