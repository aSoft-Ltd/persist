package tz.co.asoft

import kotlinx.atomicfu.AtomicRef

fun <K, V> AtomicRef<MutableMap<K, V>>.getOrPut(k: K, block: () -> V): V {
    val obj = value[k]
    if (obj != null) {
        return obj
    }
    value[k] = block()
    return getOrPut(k, block)
}