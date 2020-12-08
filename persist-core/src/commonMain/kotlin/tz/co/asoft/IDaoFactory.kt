package tz.co.asoft

import kotlinx.atomicfu.AtomicRef

interface IDaoFactory<D : Any> {
    val daoConfig: AtomicRef<D?>
    val dao get() = daoConfig.value ?: throw Exception("Dao ${this::class.simpleName} is not yet configured")
    fun configureDao(c: D): D {
        daoConfig.value = c
        return c
    }
}