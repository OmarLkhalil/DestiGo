package com.mobilebreakero.data.repoimpl

import com.mobilebreakero.data.remote.TripApi
import com.mobilebreakero.domain.model.DetailsResponse
import com.mobilebreakero.domain.repo.DetailsRepository
import com.mobilebreakero.domain.util.Response

class DetailsRepoImplementation(
    private val api: TripApi
) : DetailsRepository {

    override suspend fun getDetails(id: String): Response<DetailsResponse> {
        val response = api.getDetails(locationId = id)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Response.Success(body)
            } else {
                Response.Failure(Exception("No data found"))
            }
        } else {
            Response.Failure(Exception("Something went wrong"))
        }
    }
}