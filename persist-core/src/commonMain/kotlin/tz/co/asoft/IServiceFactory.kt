package tz.co.asoft

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic

interface IServiceFactory<S : Any> {
    val serviceConfig: AtomicRef<S?>
    val service get() = serviceConfig.value ?: throw Exception("Service ${this::class.simpleName} is not yet initialized")
    fun configureService(c: S): S {
        serviceConfig.value = c
        return c
    }
}