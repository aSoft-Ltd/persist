package tz.co.asoft

import org.w3c.dom.get
import org.w3c.dom.set
import kotlinx.browser.window

class Storage(override val name: String) : IStorage {
    private val db = window.localStorage

    private fun getTable(): dynamic {
        var table = db[name]
        if (table == null) {
            table = "{}"
        }
        return JSON.parse(table)
    }

    override fun get(key: String): String? = getTable()[key].unsafeCast<String?>()

    override fun set(key: String, value: String) {
        val table = getTable()
        table[key] = value
        db[name] = JSON.stringify(table)
    }

    override fun remove(key: String) {
        val table = getTable()
        table[key] = undefined
        db[name] = JSON.stringify(table)
    }

    override fun clear() = db.removeItem(name)
}