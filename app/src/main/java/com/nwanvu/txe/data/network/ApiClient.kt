package com.nwanvu.txe.data.network

import retrofit2.Call

/**
 * Interface for handling API calls, to support different implementations
 */
interface ApiClient {

    /**
     * Execute API call and return [Either]
     */
    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R>
}