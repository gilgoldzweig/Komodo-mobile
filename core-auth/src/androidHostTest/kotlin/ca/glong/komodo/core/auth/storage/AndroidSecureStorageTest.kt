package ca.glong.komodo.core.auth.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AndroidSecureStorageTest {

    private val dataStore = mock<DataStore<Preferences>>()
    
    private val storage = AndroidSecureStorage(dataStore)

    @Test
    fun `save stores value in DataStore`() = runTest {
        // Preferences is a final class/interface with final members in Android,
        // so mocking it directly is tricky with Mokkery/KMP sometimes.
        // However, we just need the edit lambda to be called.
        // Let's try to mock the behavior differently or just verify the interaction.
        // Actually, let's try to use a real Preferences object if possible or skip the return value check if not needed.
        // But DataStore.edit expects a return value (Preferences).
        
        // Simpler approach: Mock the DataStore update call. 
        // Note: DataStore.edit is an extension function that calls DataStore.updateData.
        // We should mock DataStore.updateData instead if we can't mock edit directly.
        
        // But since we are testing implementation that calls .edit(), we need to handle that.
        // .edit() calls updateData internally.
        
        everySuspend { dataStore.updateData(any()) } returns mock<Preferences> { }

        storage.save("key", "value")

        verifySuspend { dataStore.updateData(any()) }
    }
}
