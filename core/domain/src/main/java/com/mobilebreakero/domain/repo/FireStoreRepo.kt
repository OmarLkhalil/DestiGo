package com.mobilebreakero.domain.repo

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener


interface FireStoreRepo {

    fun addUserToFireStore(
        user: com.mobilebreakero.domain.model.AppUser,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    )
}