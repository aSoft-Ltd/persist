package tz.co.asoft

class GenericPagingSource<E : Entity>(private val dao: IDao<E>) : PagingSource<E> {
    override val predicate: (E) -> Boolean = { true }

    override suspend fun firstPage(pageSize: Int) = Page(
        pageSize = pageSize,
        data = dao.page(1, pageSize),
        pageNo = 1
    )

    override suspend fun nextOf(page: Page<E>) = Page(
        pageSize = page.pageSize,
        data = dao.page(page.pageNo + 1, page.pageSize),
        pageNo = page.pageNo + 1
    )

    override suspend fun prevOf(page: Page<E>) = Page(
        pageSize = page.pageSize,
        data = dao.page(page.pageNo - 1, page.pageSize),
        pageNo = page.pageNo - 1
    )
}