package com.example.sliideusersbook.common.repository

import com.example.sliideusersbook.userslist.model.request.UserBody
import com.example.sliideusersbook.common.api.UsersApi
import com.example.sliideusersbook.userslist.model.User
import com.example.sliideusersbook.userslist.model.responses.UserResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface UsersRepository {
    fun getUsersList(): Single<List<UserResponse>>

    fun deleteUser(user: User): Completable

    fun addUser(user: UserBody): Completable
}

class RemoteUsersRepositoryImpl @Inject constructor(private val api: UsersApi) : UsersRepository {

    override fun getUsersList(): Single<List<UserResponse>> {
        return api.geUsersList()
    }

    override fun deleteUser(user: User): Completable {
        return api.deleteUser(user.id)
    }

    override fun addUser(user: UserBody): Completable {
        return api.addUser(user)
    }
}