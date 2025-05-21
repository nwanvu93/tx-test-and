package com.nwanvu.txe.domain.api

import com.nwanvu.txe.domain.entity.UserEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserApi {
    companion object {
        private const val PARAM_SIZE = "per_page"
        private const val PARAM_SINCE = "since"
        private const val PARAM_USERNAME = "username"

        private const val USER_LIST = "users"
        private const val USER_DETAILS = "users/{$PARAM_USERNAME}"
    }

    @GET(USER_LIST)
    fun fetchUsers(
        @Query(PARAM_SIZE) pageSize: Int,
        @Query(PARAM_SINCE) since: Int,
    ): Call<List<UserEntity>>

    @GET(USER_DETAILS)
    fun getUserDetails(
        @Path(PARAM_USERNAME) username: String
    ): Call<UserEntity>
}