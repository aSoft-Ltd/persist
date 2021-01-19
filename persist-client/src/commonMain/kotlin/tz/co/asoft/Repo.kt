package tz.co.asoft

open class Repo<E : Entity>(private val dao: IDao<E>) : IRepo<E>, IDao<E> by dao