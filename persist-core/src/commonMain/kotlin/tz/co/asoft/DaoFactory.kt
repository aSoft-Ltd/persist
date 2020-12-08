package tz.co.asoft

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic

open class DaoFactory<D : Any> : IDaoFactory<D> {
    override val daoConfig: AtomicRef<D?> = atomic(null)
    override val dao get() = daoConfig.value ?: throw Exception("Dao ${this::class.simpleName} is not yet configured")
    override fun configureDao(c: D): D {
        daoConfig.value = c
        return c
    }
}