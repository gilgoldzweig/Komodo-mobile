package ca.glong.komodo.di

import ca.glong.komodo.data.local.DATA_STORE_FILE_NAME
import ca.glong.komodo.data.local.createDataStore
import org.koin.android.ext.koin.androidContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.annotation.ComponentScan
import android.content.Context

@Module
@ComponentScan("ca.glong.komodo.data.local")
class AndroidModule {
    @Single
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return createDataStore(
            producePath = {
                context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
            }
        )
    }
}

