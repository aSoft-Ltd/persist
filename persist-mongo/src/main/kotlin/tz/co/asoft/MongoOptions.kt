package tz.co.asoft

class MongoOptions(
    val host: String,
    val port: Int,
    val user: String,
    val password: String,
    val database: String
)

fun Map<String, *>.toMongoOptions(): MongoOptions {
    val host: String by this
    val port: String by this
    val user: String by this
    val password: String by this
    val database: String by this
    return MongoOptions(host, port.toInt(), user, password, database)
}
