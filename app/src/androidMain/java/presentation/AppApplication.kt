package presentation

import android.app.Application
import android.content.Context

class AppApplication : Application() {

    companion object {

        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
    }
}