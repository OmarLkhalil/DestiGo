package com.mobilebreakero.domain.model

import android.net.Uri

data class Trip (
    var id: String? = null,
    var userId: String? = null,
    var name: String? = null,
    var location: String? = null,
    var date : String? = null,
    var why : String? = null,
    var image: Uri? = null,
    var checkList : List<String>? = null,
    var places : List<String>? = null,
) {
    companion object {
        const val COLLECTION_NAME = "trips"
    }
}