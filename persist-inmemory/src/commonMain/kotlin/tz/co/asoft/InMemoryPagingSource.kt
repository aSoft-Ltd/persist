package tz.co.asoft

class InMemoryPagingSource<E : Entity>(private val dao: InMemoryDao<E>) : PagingSource<E> {
    override val predicate: (E) -> Boolean = { true }

    override suspend fun firstPage(pageSize: Int) = Page(
        pageSize = pageSize,
        data = dao.data.values.chunked(pageSize)[0],
        pageNo = 1
    )

    override suspend fun nextOf(page: Page<E>) = Page(
        pageSize = page.pageSize,
        data = dao.data.values.chunked(page.pageSize)[page.pageNo],
        pageNo = page.pageNo + 1
    )

    override suspend fun prevOf(page: Page<E>) = Page(
        pageSize = page.pageSize,
        data = dao.data.values.chunked(page.pageSize)[page.pageNo - 1],
        pageNo = page.pageNo - 1
    )
}