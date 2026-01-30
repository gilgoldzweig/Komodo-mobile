package ca.glong.komodo.android

import android.app.Application
import ca.glong.komodo.di.initKoin
import org.koin.android.ext.koin.androidContext

class KomodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@KomodoApplication)
        }
    }
}
