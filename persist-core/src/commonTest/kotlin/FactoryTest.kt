import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import tz.co.asoft.IDaoFactory
import tz.co.asoft.IServiceFactory
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class PersistFactory : IDaoFactory<Int>, IServiceFactory<String> {
    override val daoConfig: AtomicRef<Int?> = atomic(null)
    override val serviceConfig: AtomicRef<String?> = atomic(null)

    fun configure(dao: Int, service: String) {
        configureDao(dao)
        configureService(service)
    }
}

object PersistCore : PersistFactory()

class FactoryTest {

    @Test
    fun should_have_sensible_api() {
        PersistCore.configure(dao = 1, service = "Service")
        assertEquals(1, PersistCore.dao)
        assertEquals("Service", PersistCore.service)
    }
}