package com.example.podcastapp.data.data_source.local.db.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.data_source.remote.dto.BestPodcastDto

class PodcastPagingSource(
    private val api: PodcastApi
): PagingSource<Int, BestPodcastDto>() {

    override fun getRefreshKey(state: PagingState<Int, BestPodcastDto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BestPodcastDto> {
        val page: Int = params.key ?: 1

        Log.d("PagingSource", page.toString())
        return try {
            val response = api.getBestPodcasts(93, page, null, null)
            val nextKey = if(response.podcasts.size < 20) null else page + 1
            val prevKey = if(page == 1) null else page - 1
            LoadResult.Page(response.podcasts, prevKey, nextKey)
        } catch (e: Exception) {
            Log.d("PagingSource", e.stackTraceToString())
            LoadResult.Error(e)
        }
    }

}