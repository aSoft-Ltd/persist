package tz.co.asoft

import kotlinx.coroutines.CoroutineScope

interface IDao<E : Entity> {
    val scope: CoroutineScope
    var token: String?
    
    fun create(list: Collection<E>): Later<List<E>>

    fun create(t: E): Later<E>

    fun edit(list: Collection<E>): Later<List<E>>

    fun edit(t: E): Later<E>

    fun delete(list: Collection<E>): Later<List<E>>

    fun delete(t: E): Later<E>

    fun wipe(list: Collection<E>): Later<List<E>>

    fun wipe(t: E): Later<E>

    fun load(uids: Collection<String>): Later<List<E>>

    fun load(uid: String): Later<E?>

    fun page(no: Int, size: Int): Later<List<E>>

    fun all(): Later<List<E>>

    fun allDeleted(): Later<List<E>>
}
