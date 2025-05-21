package com.nwanvu.txe.data.repository

import android.content.Context
import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.data.network.ApiClient
import com.nwanvu.txe.data.network.Either
import com.nwanvu.txe.data.network.Failure
import com.nwanvu.txe.data.network.toLeft
import com.nwanvu.txe.domain.entity.UserEntity
import com.nwanvu.txe.domain.api.GithubUserApi
import com.nwanvu.txe.domain.repository.UsersRepository
import com.nwanvu.txe.utils.NetworkUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Inject

class UsersDataRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiClient: ApiClient,
    retrofit: Retrofit
) : UsersRepository {

    private val service by lazy {
        retrofit.create(GithubUserApi::class.java)
    }

    override fun fetchUsers(
        pageSize: Int, since: Int
    ): Either<Failure, List<User>> {
        return when (NetworkUtil.isConnected(context)) {
            true -> apiClient.request(service.fetchUsers(pageSize, since), { res ->
                res.map { it.toData() }
            }, emptyList())

            false -> Failure.NetworkConnection.toLeft()
        }
    }

    override fun getUserDetails(username: String): Either<Failure, User> {
        return when (NetworkUtil.isConnected(context)) {
            true -> apiClient.request(service.getUserDetails(username), { res ->
                res.toData()
            }, UserEntity.empty())

            false -> Failure.NetworkConnection.toLeft()
        }
    }

}