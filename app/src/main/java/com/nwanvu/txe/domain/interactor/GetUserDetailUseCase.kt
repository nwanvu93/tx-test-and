package com.nwanvu.txe.domain.interactor

import com.nwanvu.txe.data.model.User
import com.nwanvu.txe.data.network.Either
import com.nwanvu.txe.data.network.Failure
import com.nwanvu.txe.domain.repository.UsersRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val repository: UsersRepository) :
    UseCase<User, GetUserDetailUseCase.Params>() {

    data class Params(val username: String)

    override suspend fun run(params: Params): Either<Failure, User> {
        return repository.getUserDetails(params.username)
    }
}