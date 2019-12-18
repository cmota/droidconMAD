import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import data.ScheduleDb
import data.createDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.logging.HttpLoggingInterceptor
import presentation.AppApplication

actual object PlatformServiceLocator {

    actual val httpClientEngine: HttpClientEngine by lazy {
        OkHttp.create {
            val networkInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            addNetworkInterceptor(networkInterceptor)
        }
    }

    actual val databaseEngine: ScheduleDb by lazy {
        setupDatabase()
    }

    private fun setupDatabase(): ScheduleDb {
        val driver: SqlDriver = AndroidSqliteDriver(ScheduleDb.Schema, AppApplication.appContext, "droidcon.db")
        return createDatabase(driver)
    }
}