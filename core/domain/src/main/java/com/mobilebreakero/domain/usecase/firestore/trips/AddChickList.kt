package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import javax.inject.Inject

class AddChickList @Inject constructor(
    private val repo: TripsRepo)
{
    suspend operator fun invoke (
        checkList: List<String>,
        id: String
    ) =
        repo.addCheckList(checkList, id)

}