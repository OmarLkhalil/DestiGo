package com.example.data.repoimpl

import com.example.domain.model.AppUser
import com.example.domain.repo.FireStoreRepo
import com.example.domain.util.getCollection
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

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