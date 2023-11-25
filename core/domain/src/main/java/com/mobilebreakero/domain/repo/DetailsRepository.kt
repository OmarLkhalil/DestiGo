package com.mobilebreakero.domain.repo

import com.mobilebreakero.domain.model.DetailsResponse
import com.mobilebreakero.domain.util.Response

interface DetailsRepository{
   suspend fun getDetails(id: String) : Response<DetailsResponse>
}