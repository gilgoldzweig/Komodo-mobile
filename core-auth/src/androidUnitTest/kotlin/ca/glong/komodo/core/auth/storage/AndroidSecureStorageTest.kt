package ca.glong.komodo.core.auth.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AndroidSecureStorageTest {

    private val dataStore: DataStore<Preferences> = mockk(relaxed = true)
    
    private val storage = AndroidSecureStorage(dataStore)

    @Test
    fun `save stores value in DataStore`() = runTest {
        val transformSlot = slot<suspend (Preferences) -> Unit>()
        
        coEvery { dataStore.edit(capture(transformSlot)) } returns mockk()

        storage.save("key", "value")

        coVerify { dataStore.edit(any()) }
    }
}
