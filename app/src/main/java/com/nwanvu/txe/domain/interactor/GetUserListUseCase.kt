package com.nwanvu.txe.domain.interactor

import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.data.network.Either
import com.nwanvu.txe.data.network.Failure
import com.nwanvu.txe.domain.repository.UsersRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val repository: UsersRepository) :
    UseCase<List<User>, GetUserListUseCase.Params>() {

    data class Params(val pageSize: Int, val since: Int)

    override suspend fun run(params: Params): Either<Failure, List<User>> {
        return repository.fetchUsers(params.pageSize, params.since)
    }
}