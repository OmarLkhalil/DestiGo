package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.repo.PhotoRepository
import javax.inject.Inject

class PhotoUseCase @Inject constructor(private val repo: PhotoRepository) {
    suspend operator fun invoke(locationId: String) = repo.getPhotos(locationId)
}