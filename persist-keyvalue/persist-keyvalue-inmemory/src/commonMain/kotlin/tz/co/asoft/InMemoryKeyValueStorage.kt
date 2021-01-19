package tz.co.asoft

class InMemoryKeyValueStorage(override val namespace: String = "") : KeyValueStorage {
    val data = mutableMapOf<String, String>()
    override fun clear() = data.clear()
    override fun get(key: String): String? = data[key]
    override fun remove(key: String) {
        data.remove(key)
    }

    override fun set(key: String, value: String) {
        data[key] = value
    }
}