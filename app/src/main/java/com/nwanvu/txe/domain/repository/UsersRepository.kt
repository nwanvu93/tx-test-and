package com.nwanvu.txe.domain.repository

import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.data.network.Either
import com.nwanvu.txe.data.network.Failure

interface UsersRepository {

    /**
     * Returns a list of [User]
     */
    fun fetchUsers(pageSize: Int, since: Int): Either<Failure, List<User>>

    /**
     * Returns a detailed [User] by the [username]
     */
    fun getUserDetails(username: String): Either<Failure, User>
}