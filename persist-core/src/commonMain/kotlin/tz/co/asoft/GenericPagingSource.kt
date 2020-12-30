package tz.co.asoft

class GenericPagingSource<E : Entity>(private val dao: IDao<E>) : PagingSource<E> {
    override val predicate: (E) -> Boolean = { true }

    override fun firstPage(pageSize: Int) = dao.page(1, pageSize).then {
        Page(
            pageSize = pageSize,
            data = it,
            pageNo = 1
        )
    }

    override fun nextOf(page: Page<E>) = dao.page(page.pageNo + 1, page.pageSize).then {
        Page(
            pageSize = page.pageSize,
            data = it,
            pageNo = page.pageNo + 1
        )
    }

    override fun prevOf(page: Page<E>) = dao.page(page.pageNo - 1, page.pageSize).then {
        Page(
            pageSize = page.pageSize,
            data = it,
            pageNo = page.pageNo - 1
        )
    }
}