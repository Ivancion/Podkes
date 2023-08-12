package com.example.podcastapp.data.data_source.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.data_source.remote.dto.SearchPodcastDto

class PodcastSearchPagingSource(
    private val api: PodcastApi,
    private val query: String,
    private val genreId: Int?,
    private val region: String?,
    private val language: String?
): PagingSource<Int, SearchPodcastDto>() {

    override fun getRefreshKey(state: PagingState<Int, SearchPodcastDto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(10) ?: page.nextKey?.minus(10)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchPodcastDto> {
        val offset = params.key ?: 0

        return try {
            val response = api.searchPodcasts(
                query = query,
                language = language,
                region = region,
                genreId = genreId,
                offset = offset
            )
            for (result in response.results) {
                Log.d("PagingSource", result.toString())
            }
            val nextKey = if(offset == response.next_offset) null else response.next_offset
            val prevKey = if(offset == 0) null else offset - 10
            LoadResult.Page(response.results, prevKey, nextKey)
        } catch (e: Exception) {
            Log.d("PagingSource", e.stackTraceToString())
            LoadResult.Error(e)
        }
    }
}