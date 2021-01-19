package tz.co.asoft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

open class InMemoryDao<T : Entity>(private val prefix: String, override var token: String? = null) : IDao<T> {
    override val scope = CoroutineScope(SupervisorJob())
    internal val data = mutableMapOf<String?, T>()

    override fun all() = scope.later { data.values.filterNot { it.deleted } }

    override fun allDeleted() = scope.later { data.values.filter { it.deleted } }

    override fun create(t: T) = scope.later {
        val id = t.uid ?: prefix + "-" + data.size
        data[id] = t.apply { uid = id }
        t
    }

    override fun create(list: Collection<T>) = scope.later { list.map { create(it).await() } }

    override fun delete(t: T) = scope.later { edit(t.apply { deleted = true }).await() }

    override fun delete(list: Collection<T>) = scope.later { list.map { delete(it).await() } }

    override fun edit(t: T) = scope.later {
        data[t.uid] = t
        t
    }

    override fun edit(list: Collection<T>) = scope.later { list.map { edit(it).await() } }

    override fun load(uid: String) = scope.later { data[uid] }

    override fun load(uids: Collection<String>) = scope.later { uids.mapNotNull { load(it).await() } }

    override fun page(no: Int, size: Int) = scope.later { data.values.chunked(size).getOrNull(no - 1) ?: listOf() }

    override fun wipe(t: T) = scope.later {
        data.remove(t.uid)
        t
    }

    override fun wipe(list: Collection<T>) = scope.later { list.map { wipe(it).await() } }
}