package com.nwanvu.txe.data.network

import retrofit2.Call

interface ApiClient {
    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R>
}