package tz.co.asoft

open class InMemoryDao<T : Entity>(private val prefix: String) : IDao<T> {
    internal val data = mutableMapOf<String?, T>()
    override suspend fun all() = data.values.filterNot { it.deleted }

    override suspend fun allDeleted() = data.values.filter { it.deleted }

    override suspend fun create(t: T): T {
        val id = t.uid ?: prefix + "-" + data.size
        data[id] = t.apply { uid = id }
        return t
    }

    override suspend fun create(list: Collection<T>) = list.map { create(it) }

    override suspend fun delete(t: T): T = edit(t.apply { deleted = true })

    override suspend fun delete(list: Collection<T>) = list.map { delete(it) }

    override suspend fun edit(t: T): T {
        data[t.uid] = t
        return t
    }

    override suspend fun edit(list: Collection<T>) = list.map { edit(it) }

    override suspend fun load(uid: String): T? = data[uid]

    override suspend fun load(uids: Collection<String>) = uids.mapNotNull { load(it) }

    override suspend fun wipe(t: T): T {
        data.remove(t.uid)
        return t
    }

    override suspend fun wipe(list: Collection<T>) = list.map { wipe(it) }
}