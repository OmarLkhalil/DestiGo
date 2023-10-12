package com.mobilebreakero.domain.repo

import com.mobilebreakero.domain.model.ResultsItem
import com.mobilebreakero.domain.util.Resource

interface SearchResultRepo {

//    suspend fun getResult(text: String?, type: String?): Resource<List<ResultsItem?>>
    suspend fun getResult(text: String?, type: String?): Resource<List<ResultsItem?>>
}