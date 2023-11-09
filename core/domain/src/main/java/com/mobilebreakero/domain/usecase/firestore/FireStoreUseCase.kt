package com.mobilebreakero.domain.usecase.firestore

data class FireStoreUseCase(
    val addUser: AddUser,
    val getUserByID: GetUserById,
    val getUsers: GetUsers,
    val updateUser: UpdateUser
)