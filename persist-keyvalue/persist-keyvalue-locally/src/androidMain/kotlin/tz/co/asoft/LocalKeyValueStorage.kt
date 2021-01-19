package tz.co.asoft

import android.content.Context

class LocalKeyValueStorage(ctx: Context, override val namespace: String) : KeyValueStorage {
    private val db = ctx.getSharedPreferences(namespace, Context.MODE_PRIVATE)

    override fun get(key: String): String? = db.getString(key, null)

    override fun set(key: String, value: String) = db.edit().putString(key, value).apply()

    override fun remove(key: String) = db.edit().remove(key).apply()

    override fun clear() = db.edit().clear().apply()
}