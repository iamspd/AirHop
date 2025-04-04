package com.example.airhop.fake

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.airhop.data.repositoryImpl.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class UserPreferenceRepositoryTest {

    /**
     * Create a temporary folder for testing, create files inside it
     * and delete it after each test case.
     */
    @get:Rule
    val temporaryFolder: TemporaryFolder = TemporaryFolder.builder()
        .assureDeletion()
        .build()

    @Test
    fun userPreferenceRepository_saveAndRetrieveDataFromDataStore_verifyData() = runTest {
        /**
         * produceFile - Create a DataStore with a file created inside the [temporaryFolder].
         *
         * Note that Data Store operation happen in Coroutine scope. (IO operation)
         * We can specify that using the Factory of the DataStore.
         *
         * If we don't do that - there are chances that Coroutine responsible for IO won't
         * be cancelled properly after the test case is finished even though it is running inside the
         * runTest {} block. It can block the test case forever causing it to timeout.
         * We will use backgroundScope for this case, which will be cancelled once the main scope
         * is finished (i.e. runTest {}).
         *
         */
        val dataStore = PreferenceDataStoreFactory.create(
            produceFile = { temporaryFolder.newFile("test_store.preferences_pb") },
            scope = backgroundScope
        )

        val userPreferencesRepository = UserPreferencesRepository(dataStore)
        userPreferencesRepository.saveSearchQuery("test_query")

        val savedQuery = userPreferencesRepository.getSearchQueryPref().first()
        assertEquals("test_query", savedQuery)
    }
}