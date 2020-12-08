package tz.co.asoft

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic

open class DaoAndServiceFactory<D : Any, S : Any> : IDaoFactory<D>, IServiceFactory<S> {
    override val daoConfig: AtomicRef<D?> = atomic(null)
    override val serviceConfig: AtomicRef<S?> = atomic(null)
    fun configure(d: D, s: S): Pair<D, S> {
        daoConfig.value = d
        serviceConfig.value = s
        return d to s
    }
}