package com.example.sliideusersbook.common.api

import com.example.sliideusersbook.userslist.model.request.UserBody
import com.example.sliideusersbook.userslist.model.responses.UserResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface UsersApi {
    @GET("users")
    fun geUsersList(@Query(value = "page") pageNumber: Int? = null): Single<List<UserResponse>>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: String): Completable

    @POST("users")
    fun addUser(@Body user: UserBody): Completable
}