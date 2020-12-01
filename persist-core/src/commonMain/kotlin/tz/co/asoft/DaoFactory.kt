package tz.co.asoft

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic

abstract class DaoFactory<D : Any> {
    private val daoConfig: AtomicRef<D?> = atomic(null)
    val dao get() = daoConfig.value ?: throw Exception("Dao ${this::class.simpleName} is not yet configured")
    fun init(c: D) : D {
        daoConfig.value = c
        return c
    }
}