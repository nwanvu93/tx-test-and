package com.nwanvu.txe.data

import com.nwanvu.txe.domain.entity.ResponseEntity
import com.nwanvu.txe.data.network.ApiClient
import com.nwanvu.txe.data.network.Either
import com.nwanvu.txe.data.network.Either.Left
import com.nwanvu.txe.data.network.Failure
import com.nwanvu.txe.data.network.toLeft
import com.nwanvu.txe.data.network.toRight
import retrofit2.Call
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * Implementation of [ApiClient] for handling API calls
 */
class DataApiClient @Inject constructor() : ApiClient {

    /**
     * Execute api call and return [Either]
     */
    override fun <T, R> request(
        call: Call<T>, transform: (T) -> R, default: T
    ): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> handleResponseSuccess(response.body(), transform, default)
                false -> handleResponseError(response)
            }
        } catch (exception: Throwable) {
            when {
                call.isCanceled -> Failure.CancelRequestFailure.toLeft()
                exception is SocketTimeoutException -> Failure.SocketTimeoutFailure.toLeft()
                else -> Failure.ServerError.toLeft()
            }
        }
    }

    /**
     * Mapping response body from [T] to [Either]
     */
    private fun <T, R> handleResponseSuccess(
        body: T?, transform: (T) -> R, default: T
    ): Either<Failure, R> {
        val data = body ?: default
        val responseEntity = data as? ResponseEntity<*>
        return when {
            responseEntity == null -> transform(data).toRight()
            responseEntity.isSuccess() -> transform(data).toRight()
            else -> Failure.ApiFailure(
                responseEntity.error,
            ).toLeft()
        }
    }

    /**
     * Handle response error and return [Failure]
     */
    private fun handleResponseError(response: Response<*>): Left<Failure> {
        return Failure.ServerError.toLeft()
    }
}



