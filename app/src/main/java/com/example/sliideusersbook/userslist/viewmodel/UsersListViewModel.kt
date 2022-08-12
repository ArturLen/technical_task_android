package com.example.sliideusersbook.userslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sliideusersbook.core.rx.SchedulersProvider
import com.example.sliideusersbook.userslist.model.User
import com.example.sliideusersbook.userslist.ui.ErrorActionType
import com.example.sliideusersbook.userslist.ui.UsersListUIState
import com.example.sliideusersbook.common.usecase.UsersUseCase
import com.example.sliideusersbook.userslist.model.UserGender
import com.example.sliideusersbook.userslist.model.UserStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val useCase: UsersUseCase,
    private val appSchedulers: SchedulersProvider
) : ViewModel() {

    private val usersListMutableLiveData = MutableLiveData<UsersListUIState>()
    val userListLiveData: LiveData<UsersListUIState> = usersListMutableLiveData

    fun loadUsers() {
        usersListMutableLiveData.value = UsersListUIState.Loading

        useCase.getUsersList()
            .observeOn(appSchedulers.main())
            .subscribeOn(appSchedulers.io())
            .subscribe({
                usersListMutableLiveData.value = UsersListUIState.UsersListLoaded(it)
            }) { onFailure(it, ErrorActionType.Loading) }
    }

    fun deleteUser(user: User) {
        usersListMutableLiveData.value = UsersListUIState.Loading

        useCase.deleteUser(user)
            .observeOn(appSchedulers.main())
            .subscribeOn(appSchedulers.io())
            .subscribe({
                usersListMutableLiveData.value = UsersListUIState.UsersDeleted
            }) { onFailure(it, ErrorActionType.Removing) }
    }

    fun addUser(username: String, email: String, gender: UserGender, status: UserStatus) {
        usersListMutableLiveData.value = UsersListUIState.Loading

        useCase.addUser(username, email, gender, status)
            .observeOn(appSchedulers.main())
            .subscribeOn(appSchedulers.io())
            .subscribe({
                usersListMutableLiveData.value = UsersListUIState.UsersAdded
            }) { onFailure(it, ErrorActionType.Adding) }
    }

    private fun onFailure(throwable: Throwable, actionType: ErrorActionType) {
        usersListMutableLiveData.value = UsersListUIState.Error(actionType)
    }
}