package com.mobilebreakero.domain.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mobilebreakero.domain.model.DataItem
import com.mobilebreakero.domain.model.PlaceItem
import com.mobilebreakero.domain.model.PlacesItem
import com.mobilebreakero.domain.repo.SearchRepository
import com.mobilebreakero.domain.repo.SearchResultRepo

//class SearchPlacesPagingSource(
//    private val repo: SearchResultRepo,
//    private val type: String,
//    private val language: String,
//    private val keyword: String
//) : PagingSource<Int, DataItem>() {
//    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
//        return null
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
//        return try {
//            val response = repo.searchPlaces(
//                query = keyword,
//                language = language,
//                filter = type
//                )
//            if (response.data!!.isNotEmpty()) {
//                LoadResult.Page(
//                    data = response!!.data,
//                    prevKey = if (params.key == 1) null else params.key?.minus(1),
//                    nextKey = if (response.data.isEmpty()) null else params.key?.plus(1)
//                )
//            } else {
//                LoadResult.Error(Exception("No data available"))
//            }
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}
