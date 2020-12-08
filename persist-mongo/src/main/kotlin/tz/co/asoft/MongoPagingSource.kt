package tz.co.asoft

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MongoPagingSource<E : Entity>(private val dao: IMongoDao<E>) : PagingSource<E> {
    override val predicate: (E) -> Boolean = { true }

    suspend fun load(pageNo: Int, pageSize: Int): Page<E> {
        require(pageNo > 0) { "Page start from one" }
        val filters = Filters.eq("deleted", false)
        val sorts = Sorts.ascending("uid")
        val skips = (pageNo - 1) * pageSize
        return Page(
            pageSize = pageSize,
            data = dao.collection.find(filters).sort(sorts).skip(skips).limit(pageSize).mapNotNull {
                it.to(dao.serializer)
            },
            pageNo = 1
        )
    }

    override suspend fun firstPage(pageSize: Int) = load(pageNo = 1, pageSize)

    override suspend fun nextOf(page: Page<E>) = load(pageNo = page.pageNo + 1, page.pageSize)

    override suspend fun prevOf(page: Page<E>) = load(pageNo = page.pageNo - 1, page.pageSize)
}