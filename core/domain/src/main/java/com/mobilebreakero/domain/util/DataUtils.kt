package com.mobilebreakero.domain.util

import com.google.firebase.firestore.auth.User
import com.mobilebreakero.domain.model.AppUser

object DataUtils {
    var user: com.mobilebreakero.domain.model.AppUser? = null
    var firebaseUser: User? = null
}
