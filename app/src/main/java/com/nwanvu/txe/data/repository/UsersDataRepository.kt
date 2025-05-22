package com.nwanvu.txe.data.repository

import android.content.Context
import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.data.network.ApiClient
import com.nwanvu.txe.data.network.Either
import com.nwanvu.txe.data.network.Failure
import com.nwanvu.txe.domain.api.GithubUserApi
import com.nwanvu.txe.domain.entity.UserEntity
import com.nwanvu.txe.domain.repository.UsersRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Implementation of [UsersRepository] that handles data operations related to GitHub users.
 *
 * This repository uses an [ApiClient] to interact with the remote GitHub API via [GithubUserApi].
 * It also performs network connectivity checks using [NetworkUtil] before making requests.
 *
 * @constructor Injects the required dependencies.
 * @param context Application context used for network checks.
 * @param apiClient Wrapper for making safe API requests.
 * @param retrofit Retrofit instance used to create the [GithubUserApi] service.
 */
class UsersDataRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiClient: ApiClient,
    retrofit: Retrofit
) : UsersRepository {

    /**
     * Lazy initialization of the [GithubUserApi] service using Retrofit.
     */
    private val service by lazy {
        retrofit.create(GithubUserApi::class.java)
    }

    /**
     * Fetches a list of GitHub users from the API.
     */
    override fun fetchUsers(
        pageSize: Int, since: Int
    ): Either<Failure, List<User>> {
        return apiClient.request(service.fetchUsers(pageSize, since), { res ->
            res.map { it.toData() }
        }, emptyList())
    }

    /**
     * Fetches detailed information about a specific GitHub user.
     */
    override fun getUserDetails(username: String): Either<Failure, User> {
        return apiClient.request(service.getUserDetails(username), { res ->
            res.toData()
        }, UserEntity.empty())
    }
}