package com.nwanvu.txe.domain.interactor

import android.content.Context
import com.nwanvu.txe.data.MockDispatcher
import com.nwanvu.txe.data.DataApiClient
import com.nwanvu.txe.data.network.ApiClient
import com.nwanvu.txe.data.network.getOrElse
import com.nwanvu.txe.data.repository.UsersDataRepository
import com.nwanvu.txe.domain.interactor.GetUserListUseCase
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class GetUserDetailUseCaseTest {
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

    private val userDetailUC = GetUserDetailUseCase(dataRepository)

    /**
     * Test fetching user details successfully
     * assumes the mock JSON contains user details for "nwanvu93"
     */
    @Test
    fun getUserDetails_success() {
        runTest {
            val username = "nwanvu93"
            val profileUrl = "https://github.com/nwanvu93"

            val result = userDetailUC.run(GetUserDetailUseCase.Params(username)).getOrElse(null)
            assertEquals(username, result?.username)
            assertEquals(profileUrl, result?.profileUrl)
        }
    }

    /**
     * Test fetching user details unsuccessfully
     */
    @Test
    fun getUserDetails_failed() {
        runTest {
            val result = userDetailUC.run(GetUserDetailUseCase.Params("invalid_user")).getOrElse(null)
            assertEquals(null, result)
        }
    }
}