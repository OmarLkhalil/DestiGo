package com.mobilebreakero.domain.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mobilebreakero.domain.model.PlaceItem
import com.mobilebreakero.domain.repo.SearchResultRepo

class PlacesPagingSource(
    private val repo: SearchResultRepo,
    private val location: String,
    private val radius: Int,
    private val type: String,
    private val language: String,
    private val keyword: String
) : PagingSource<Int, PlaceItem>() {
    override fun getRefreshKey(state: PagingState<Int, PlaceItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaceItem> {
        return try {
            val response = repo.getResult(
                location = location,
                radius = radius,
                type = type,
                language = language,
                keyword = keyword
            )
            if (response?.places!!.isNotEmpty()) {
                LoadResult.Page(
                    data = response.places,
                    prevKey = if (params.key == 1) null else params.key?.minus(1),
                    nextKey = if (response.places.isEmpty()) null else params.key?.plus(1)
                )
            } else {
                LoadResult.Error(Exception("No data available"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
