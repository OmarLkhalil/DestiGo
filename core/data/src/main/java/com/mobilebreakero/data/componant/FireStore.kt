package com.mobilebreakero.data.componant

import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.repo.FireStoreRepo
import com.mobilebreakero.domain.util.DataUtils

fun createFireStoreUser(repository: FireStoreRepo, uid: String?, name: String?, email: String?) {
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