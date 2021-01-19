package tz.co.asoft

import java.io.File

class LocalKeyValueStorage(override val namespace: String = "", val file: File = File("$namespace.storage.json")) : KeyValueStorage {
    init {
        if(!file.exists()) {
            file.createNewFile()
        }
    }
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