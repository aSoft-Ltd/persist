package tz.co.asoft

interface IStorage {
    val name: String
    fun get(key: String): String?
    fun set(key: String, value: String)
    fun remove(key: String)
    fun clear()
}