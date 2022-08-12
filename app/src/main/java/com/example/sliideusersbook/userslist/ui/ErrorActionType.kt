package com.example.sliideusersbook.userslist.ui

sealed class ErrorActionType {
    object Loading : ErrorActionType()
    object Adding : ErrorActionType()
    object Removing : ErrorActionType()
}