package tz.co.asoft

interface KeyValueStorage {
    val namespace: String
    fun get(key: String): String?
    fun set(key: String, value: String)
    fun remove(key: String)
    fun clear()
}