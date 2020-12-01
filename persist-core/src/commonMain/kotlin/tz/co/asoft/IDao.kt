package tz.co.asoft

interface IDao<T : Entity> {

    suspend fun create(list: Collection<T>): List<T>

    suspend fun create(t: T): T

    suspend fun edit(list: Collection<T>): List<T>

    suspend fun edit(t: T): T

    suspend fun delete(list: Collection<T>): List<T>

    suspend fun delete(t: T): T

    suspend fun wipe(list: Collection<T>): List<T>

    suspend fun wipe(t: T): T

    suspend fun load(uids: Collection<String>): List<T>

    suspend fun load(uid: String): T?

    suspend fun all(): List<T>

    suspend fun allDeleted(): List<T>
}
