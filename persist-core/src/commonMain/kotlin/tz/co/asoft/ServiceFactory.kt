package tz.co.asoft

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic

abstract class ServiceFactory<S : Any> {
    private val serviceConfig: AtomicRef<S?> = atomic(null)
    val service get() = serviceConfig.value ?: throw Exception("Service ${this::class.simpleName} is not yet initialized")
    fun init(c: S): S {
        serviceConfig.value = c
        return c
    }
}