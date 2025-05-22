package com.nwanvu.txe.domain.interactor

import android.content.Context
import com.nwanvu.txe.data.DataApiClient
import com.nwanvu.txe.data.MockDispatcher
import com.nwanvu.txe.data.network.ApiClient
import com.nwanvu.txe.data.network.getOrElse
import com.nwanvu.txe.data.repository.UsersDataRepository
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class GetUserListUseCaseTest {
    private val mockWebServer = MockWebServer().apply {
        dispatcher = MockDispatcher.RequestDispatcher()
    }
    private val retrofit = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val context: Context = mock<Context>()
    private val apiClient: ApiClient = DataApiClient()
    private val dataRepository = UsersDataRepository(
        context, apiClient, retrofit
    )

    private val userListUC = GetUserListUseCase(dataRepository)

    /**
     * Test fetching users successfully
     */
    @Test
    fun fetchUsers_success() {
        runTest {
            val perPage = 20
            val since = 0
            // Call fetchUsers and assert the expected size or contents
            val result = userListUC.run(GetUserListUseCase.Params(perPage, since)).getOrElse(emptyList())
            assertEquals(perPage, result.size)

            val expectedSecondUsername = "defunkt"
            assertEquals(expectedSecondUsername, result[1].username)
        }
    }

    /**
     * Test fetching users unsuccessfully
     */
    @Test
    fun fetchUsers_failed() {
        runTest {
            val result = userListUC.run(GetUserListUseCase.Params(0, 0)).getOrElse(emptyList())
            assertTrue(result.isEmpty())
        }
    }
}