import org.junit.Test
import tz.co.asoft.LocalKeyValueStorage
import tz.co.asoft.expect
import tz.co.asoft.toBe
import java.io.File

class LocalKeyValueStorageTest {

    private val file = File("build/test-sandbox/storage.json")

    @Test
    fun `should at lease create a file in the classpath`() {
        val storage = LocalKeyValueStorage(file = file)
        expect(storage.file.exists()).toBe(true)
    }

    @Test
    fun `should save and get values into the file`() {
        val storage = LocalKeyValueStorage(file = file)
        storage.set("tz.co.asoft.test", "true")
        expect(storage.file.readText().contains("tz.co.asoft.test")).toBe(true)
        val value = storage.get("tz.co.asoft.test")
        expect(value).toBe("true")
    }
}