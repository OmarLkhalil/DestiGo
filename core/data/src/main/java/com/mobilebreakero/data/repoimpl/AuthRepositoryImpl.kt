package com.mobilebreakero.data.repoimpl


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mobilebreakero.data.componant.createFireStoreUser
import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.repo.FireStoreRepo
import com.mobilebreakero.domain.util.Resource
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
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if (!task.isSuccessful) {
                    //show error message
                    task.exception!!.localizedMessage?.let { Log.e("Error create user", it) }
                } else {
                    createFireStoreUser(
                        repository = repository,
                        uid = task.result.user?.uid,
                        name = name, email = email
                    )
                }
            }.await()
            //result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            return Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

}