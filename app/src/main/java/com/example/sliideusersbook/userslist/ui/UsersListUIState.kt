package com.example.sliideusersbook.userslist.ui

import com.example.sliideusersbook.userslist.model.User

sealed class UsersListUIState {
    object Loading : UsersListUIState()
    object UsersAdded: UsersListUIState()
    object UsersDeleted: UsersListUIState()
    class Error(val type: ErrorActionType) : UsersListUIState()
    class UsersListLoaded(val users: List<User>) : UsersListUIState()
}