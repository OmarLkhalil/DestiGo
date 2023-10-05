package com.mobilebreakero.data.repoimpl

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.repo.FireStoreRepo
import com.mobilebreakero.domain.util.getCollection

class FireStoreRepoImpl : FireStoreRepo {
    override fun addUserToFireStore(
        user: AppUser,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) {
        val userCollection = getCollection(AppUser.COLLECTION_NAME)
        val userDoc = userCollection.document(user.id!!)
        userDoc.set(user)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }
}