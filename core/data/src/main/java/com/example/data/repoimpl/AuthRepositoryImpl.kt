package com.example.data.repoimpl

import android.util.Log
import com.example.domain.model.AppUser
import com.example.domain.repo.AuthRepository
import com.example.domain.repo.FireStoreRepo
import com.example.domain.util.DataUtils
import com.example.domain.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mobilebreakero.domain.util.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val repository: FireStoreRepo
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if (!task.isSuccessful) {
                    //show error message
                    task.exception!!.localizedMessage?.let { Log.e("Error create user", it) }
                } else {
                    createFireStoreUser(task.result.user?.uid, name = name, email = email)
                }
            }.await()
            //result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            return Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    private fun createFireStoreUser(uid: String?, name: String?, email: String?) {
        val user = AppUser(
            id = uid,
            name = name,
            email = email
        )
        repository.addUserToFireStore(user, {
            DataUtils.user = user
        }, {

        })
    }

}