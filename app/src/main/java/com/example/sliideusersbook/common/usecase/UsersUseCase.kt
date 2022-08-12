package com.example.sliideusersbook.common.usecase

import com.example.sliideusersbook.userslist.model.request.UserBody
import com.example.sliideusersbook.userslist.model.User
import com.example.sliideusersbook.userslist.model.responses.UserResponse
import com.example.sliideusersbook.common.repository.UsersRepository
import com.example.sliideusersbook.userslist.model.UserGender
import com.example.sliideusersbook.userslist.model.UserStatus
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface UsersUseCase {
    fun getUsersList(): Single<List<User>>

    fun addUser(
        username: String,
        email: String,
        gender: UserGender,
        status: UserStatus
    ): Completable

    fun deleteUser(user: User): Completable
}

class UsersUseCaseImpl @Inject constructor(private val repository: UsersRepository) : UsersUseCase {

    override fun getUsersList(): Single<List<User>> {
        return repository.getUsersList().map { convertResult(it) }
    }

    override fun addUser(
        username: String,
        email: String,
        gender: UserGender,
        status: UserStatus
    ): Completable {
        val userBody = UserBody(username, email, gender.gender, status.status)
        return repository.addUser(userBody)
    }

    override fun deleteUser(user: User): Completable {
        return repository.deleteUser(user)
    }

    private fun convertResult(listResponse: List<UserResponse>): List<User> {
        return if (listResponse.isEmpty()) {
            listOf()
        } else {
            val usersList: MutableList<User> = mutableListOf()
            for (item in listResponse) {
                usersList.add(User(
                    item.id,
                    item.name,
                    item.email,
                    gender = UserGender.values().firstOrNull { it.gender == item.gender }
                        ?: UserGender.MALE,
                    status = UserStatus.values().firstOrNull { it.status == item.status }
                        ?: UserStatus.ACTIVE)
                )
            }
            usersList
        }
    }
}