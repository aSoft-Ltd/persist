package tz.co.asoft

import android.content.Context

class Storage(ctx: Context, override val name: String) : IStorage {
    private val db = ctx.getSharedPreferences(name, Context.MODE_PRIVATE)

    override fun get(key: String): String? = db.getString(key, null)

    override fun set(key: String, value: String) = db.edit().putString(key, value).apply()

    override fun remove(key: String) = db.edit().remove(key).apply()

    override fun clear() = db.edit().clear().apply()
}