package tz.co.asoft

import kotlinx.atomicfu.atomic
import kotlin.reflect.KClass

@PublishedApi
internal val singletons = atomic(mutableMapOf<KClass<*>, Any>())

inline fun <reified T : Any> single(noinline block: () -> T): T = singletons.getOrPut(T::class, block) as T

@PublishedApi
internal val daos = atomic(mutableMapOf<KClass<*>, IDao<*>>())

inline fun <reified E : Any, D : IDao<E>> dao(noinline block: () -> D) = daos.getOrPut(E::class, block) as D

@PublishedApi
internal val repos = atomic(mutableMapOf<KClass<*>, IRepo<*>>())

inline fun <reified E : Any, R : IRepo<E>> repo(noinline block: () -> R) = repos.getOrPut(E::class, block) as R