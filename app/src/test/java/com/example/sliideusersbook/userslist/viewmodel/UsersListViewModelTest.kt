package com.example.sliideusersbook.userslist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.TestSchedulers
import com.example.sliideusersbook.common.usecase.UsersUseCase
import com.example.sliideusersbook.userslist.model.User
import com.example.sliideusersbook.userslist.model.UserGender
import com.example.sliideusersbook.userslist.model.UserStatus
import com.example.sliideusersbook.userslist.ui.ErrorActionType
import com.example.sliideusersbook.userslist.ui.UsersListUIState
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

val USER = User("111", "test user", "test@gmail.com", UserGender.MALE, UserStatus.ACTIVE)
val USERS_LIST = listOf(USER, USER)

@RunWith(MockitoJUnitRunner::class)
internal class UsersListViewModelTest {

    @Mock
    private lateinit var userCase: UsersUseCase

    private lateinit var viewModel: UsersListViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = UsersListViewModel(userCase, TestSchedulers())
    }

    @Test
    fun `given loading users action success, when loadUsers(), then ui state is changed correctly`() {
        whenever(userCase.getUsersList()).thenReturn(Single.just(USERS_LIST))

        viewModel.loadUsers()

        assertThat(viewModel.userListLiveData.value).isInstanceOf(UsersListUIState.UsersListLoaded::class.java)
    }

    @Test
    fun `given loading users action error, when loadUsers() then, ui state is changed correctly`() {
        whenever(userCase.getUsersList()).thenReturn(Single.error(Throwable()))

        viewModel.loadUsers()

        val state = viewModel.userListLiveData.value
        assertThat(state).isInstanceOf(UsersListUIState.Error::class.java)
        assertThat((state as UsersListUIState.Error).type).isInstanceOf(ErrorActionType.Loading.javaClass)
    }

    @Test
    fun `given user delete action success, when deleteUser(), then ui state is changed correctly`() {
        whenever(userCase.deleteUser(any())).thenReturn(Completable.complete())

        viewModel.deleteUser(USER)

        assertThat(viewModel.userListLiveData.value).isInstanceOf(UsersListUIState.UsersDeleted.javaClass)
    }

    @Test
    fun `given user delete action failure, when deleteUser(), then ui state is changed correctly`() {
        whenever(userCase.deleteUser(any())).thenReturn(Completable.error(Throwable()))

        viewModel.deleteUser(USER)

        val state = viewModel.userListLiveData.value
        assertThat(state).isInstanceOf(UsersListUIState.Error::class.java)
        assertThat((state as UsersListUIState.Error).type).isInstanceOf(ErrorActionType.Removing.javaClass)
    }

    @Test
    fun `given user add action success, when addUser(), then ui state is changed correctly`() {
        whenever(userCase.addUser(any(), any(), any(), any())).thenReturn(Completable.complete())

        viewModel.addUser("name", "email", UserGender.MALE, UserStatus.ACTIVE)

        assertThat(viewModel.userListLiveData.value).isInstanceOf(UsersListUIState.UsersAdded.javaClass)
    }

    @Test
    fun `given user add action failure, when addUser(), then ui state is changed correctly`() {
        whenever(userCase.addUser(any(), any(), any(), any())).thenReturn(Completable.error(Throwable()))

        viewModel.addUser("name", "email", UserGender.MALE, UserStatus.ACTIVE)

        val state = viewModel.userListLiveData.value
        assertThat(state).isInstanceOf(UsersListUIState.Error::class.java)
        assertThat((state as UsersListUIState.Error).type).isInstanceOf(ErrorActionType.Adding.javaClass)
    }
}


