package com.mobilebreakero.domain.usecase.firestore

data class FireStoreUseCase(
    val addUser: AddUser,
    val getUsers: GetUsers,
    val updateUser: UpdateUser
)