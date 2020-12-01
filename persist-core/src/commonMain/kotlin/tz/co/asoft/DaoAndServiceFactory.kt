package tz.co.asoft

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic

abstract class DaoAndServiceFactory<D : Any, S : Any> {
    private val daoConfig: AtomicRef<D?> = atomic(null)
    val dao get() = daoConfig.value ?: throw Exception("Dao ${this::class.simpleName} is not yet initialized")

    private val serviceConfig: AtomicRef<S?> = atomic(null)
    val service get() = serviceConfig.value ?: throw Exception("Service ${this::class.simpleName} is not yet initialized")

    fun init(d: D, s: S): Pair<D, S> {
        daoConfig.value = d
        serviceConfig.value = s
        return d to s
    }
}