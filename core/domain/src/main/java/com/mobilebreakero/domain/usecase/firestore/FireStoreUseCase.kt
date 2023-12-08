package com.mobilebreakero.domain.usecase.firestore

data class FireStoreUseCase(
    val addUser: AddUser,
    val getUserByID: GetUserById,
    val getUsers: GetUsers,
    val updateUser: UpdateUser,
    val updateUserLocation: UpdateLocation,
    val updateUserPhotoUrl: UpdateProfilePhoto,
    val updateUserStatus: UpdateStatus,
    val updateUserInterestedPlaces: UpdateInterestedPlaces,
    val getInterestedPlaces: GetInterestedPlaces,
)