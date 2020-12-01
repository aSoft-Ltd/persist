package tz.co.asoft

open class Repo<T : Entity>(private val dao: IDao<T>) : IRepo<T>, IDao<T> by dao