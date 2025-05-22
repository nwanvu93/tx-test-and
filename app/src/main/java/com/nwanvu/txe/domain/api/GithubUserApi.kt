package com.nwanvu.txe.domain.api

import com.nwanvu.txe.domain.entity.UserEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API interface for accessing GitHub user-related endpoints.
 *
 * Defines methods to fetch a list of users and retrieve individual user details.
 */
interface GithubUserApi {
    companion object {
        private const val PARAM_SIZE = "per_page"
        private const val PARAM_SINCE = "since"
        private const val PARAM_USERNAME = "username"

        private const val USER_LIST = "users"
        private const val USER_DETAILS = "users/{$PARAM_USERNAME}"
    }

    /**
     * Retrieves a paginated list of GitHub users starting from a specified index.
     *
     * @param pageSize The number of users to retrieve per page.
     * @param since The index to start the list from (for pagination).
     * @return A [Call] that yields a list of [UserEntity] objects.
     */
    @GET(USER_LIST)
    fun fetchUsers(
        @Query(PARAM_SIZE) pageSize: Int,
        @Query(PARAM_SINCE) since: Int,
    ): Call<List<UserEntity>>

    /**
     * Retrieves detailed information about a specific GitHub user.
     *
     * @param username The GitHub username to fetch details for.
     * @return A [Call] that yields a [UserEntity] with detailed user information.
     */
    @GET(USER_DETAILS)
    fun getUserDetails(
        @Path(PARAM_USERNAME) username: String
    ): Call<UserEntity>
}