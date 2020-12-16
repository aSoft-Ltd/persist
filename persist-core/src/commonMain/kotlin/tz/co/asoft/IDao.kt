package tz.co.asoft

interface IDao<E : Entity> {

    suspend fun create(list: Collection<E>): List<E>

    suspend fun create(t: E): E

    suspend fun edit(list: Collection<E>): List<E>

    suspend fun edit(t: E): E

    suspend fun delete(list: Collection<E>): List<E>

    suspend fun delete(t: E): E

    suspend fun wipe(list: Collection<E>): List<E>

    suspend fun wipe(t: E): E

    suspend fun load(uids: Collection<String>): List<E>

    suspend fun load(uid: String): E?

    suspend fun page(no: Int, size: Int): List<E>

    suspend fun all(): List<E>

    suspend fun allDeleted(): List<E>
}
