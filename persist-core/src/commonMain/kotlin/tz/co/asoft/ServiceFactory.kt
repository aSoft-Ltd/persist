package tz.co.asoft

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic

open class ServiceFactory<S : Any> : IServiceFactory<S> {
    override val serviceConfig: AtomicRef<S?> = atomic(null)
    override val service get() = serviceConfig.value ?: throw Exception("Service ${this::class.simpleName} is not yet initialized")
    override fun configureService(c: S): S {
        serviceConfig.value = c
        return c
    }
}