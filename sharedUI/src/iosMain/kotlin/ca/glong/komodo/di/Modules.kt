package ca.glong.komodo.di

import ca.glong.komodo.data.local.DATA_STORE_FILE_NAME
import ca.glong.komodo.data.local.createDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.annotation.ComponentScan

@Module
@ComponentScan("ca.glong.komodo.data.local")
class IosModule {
    @OptIn(ExperimentalForeignApi::class)
    @Single
    fun provideDataStore(): DataStore<Preferences> {
        return createDataStore(
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/$DATA_STORE_FILE_NAME"
            }
        )
    }
}

