package com.example.domain.util

import com.example.domain.model.AppUser
import com.google.firebase.firestore.auth.User

object DataUtils {
    var user: AppUser? = null
    var firebaseUser: User? = null
}
