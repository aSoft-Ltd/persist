package tz.co.asoft

class Storage : IStorage {
    val data = mutableMapOf<String, String>()
    override val name: String get() = ""
    override fun clear() = data.clear()
    override fun get(key: String): String? = data[key]
    override fun remove(key: String) {
        data.remove(key)
    }

    override fun set(key: String, value: String) {
        data[key] = value
    }
}