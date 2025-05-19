package com.nwanvu.txe.data.network

import com.nwanvu.txe.data.entity.ResponseEntity
import com.nwanvu.txe.data.entity.UserEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserApi {
    companion object {
        private const val PARAM_SIZE = "per_page"
        private const val PARAM_SINCE = "since"
        private const val PARAM_USERNAME = "username"

        private const val USER_LIST = "https://api.github.com/users"
        private const val USER_DETAILS = "https://api.github.com/users/{$PARAM_USERNAME}"
    }

    @GET(USER_LIST)
    fun fetchUsers(
        @Query(PARAM_SIZE) pageSize: Int = 20,
        @Query(PARAM_SINCE) since: Int = 0,
    ): Call<ResponseEntity<List<UserEntity>>>

    @GET(USER_DETAILS)
    fun getUserDetails(
        @Path(PARAM_USERNAME) username: String
    ): Call<ResponseEntity<UserEntity>>
}