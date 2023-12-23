package com.mobilebreakero.domain.usecase.firestore

import com.mobilebreakero.domain.usecase.firestore.user.AddUser
import com.mobilebreakero.domain.usecase.firestore.user.GetInterestedPlaces
import com.mobilebreakero.domain.usecase.firestore.user.GetUserById
import com.mobilebreakero.domain.usecase.firestore.user.GetUsers
import com.mobilebreakero.domain.usecase.firestore.user.UpdateInterestedPlaces
import com.mobilebreakero.domain.usecase.firestore.user.UpdateLocation
import com.mobilebreakero.domain.usecase.firestore.user.UpdateProfilePhoto
import com.mobilebreakero.domain.usecase.firestore.user.UpdateStatus
import com.mobilebreakero.domain.usecase.firestore.user.UpdateUser

data class UserUseCase(
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