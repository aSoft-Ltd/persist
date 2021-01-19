package tz.co.asoft

import java.io.File

class LocalKeyValueStorage(override val namespace: String = "namespace", val file: File) : KeyValueStorage {
    init {
        val parent = file.parentFile
        if (!parent.exists()) parent.mkdirs()
        if (!file.exists()) file.createNewFile()
        require(namespace.isNotBlank()) { "Blank/Empty namespaces are not allowed" }
    }

    private val mapper = Mapper { prettyPrint = true }

    private fun File.readMap() = try {
        (mapper.decodeFromString(readText()) as? Map<String, String>) ?: mapOf()
    } catch (_: Throwable) {
        mapOf()
    }

    private fun File.write(map: Map<String, Any?>) = writeText(mapper.encodeToString(map))

    override fun clear() = file.write(mapOf())

    override fun get(key: String): String? = file.readMap()[key]
    override fun remove(key: String) {
        val map = file.readMap().toMutableMap()
        map.remove(key)
        file.write(map)
    }

    override fun set(key: String, value: String) {
        val map = file.readMap().toMutableMap()
        map[key] = value
        file.write(map)
    }
}