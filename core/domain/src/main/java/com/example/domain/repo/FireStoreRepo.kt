package com.example.domain.repo

import com.example.domain.model.AppUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

interface FireStoreRepo {

    fun addUserToFireStore(
        user: AppUser,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    )
}