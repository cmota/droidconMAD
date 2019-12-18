import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import data.ScheduleDb
import data.createDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.ios.Ios

@ThreadLocal
actual object PlatformServiceLocator {

    actual val httpClientEngine: HttpClientEngine by lazy {
        Ios.create()
    }
    actual val databaseEngine: ScheduleDb by lazy {
        setupDatabase()
    }

    private fun setupDatabase(): ScheduleDb {
        val driver: SqlDriver = NativeSqliteDriver(ScheduleDb.Schema, "droidcon.db")
        return createDatabase(driver)
    }
}