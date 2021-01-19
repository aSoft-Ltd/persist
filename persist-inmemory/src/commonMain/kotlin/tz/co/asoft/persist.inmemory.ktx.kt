package tz.co.asoft

inline fun <reified T : Entity> InMemoryDao(token: String? = null): InMemoryDao<T> = InMemoryDao(
    prefix = T::class.simpleName ?: "entity",
    token = token
)

inline fun <reified T : NamedEntity> UniqueNameInMemoryDao(token: String? = null): UniqueNameInMemoryDao<T> = UniqueNameInMemoryDao(
    prefix = T::class.simpleName ?: "entity",
    token = token
)