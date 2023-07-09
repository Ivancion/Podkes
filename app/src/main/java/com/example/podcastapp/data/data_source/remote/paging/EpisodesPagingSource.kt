package com.example.podcastapp.data.data_source.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.data_source.remote.dto.EpisodeDto
import com.example.podcastapp.data.data_source.remote.dto.PodcastDetailsDto

class EpisodesPagingSource(
    private val api: PodcastApi,
    private val podcastId: String,
    private val podcastDetailsData: (PodcastDetailsDto) -> Unit
): PagingSource<Int, EpisodeDto>() {

    private var nextEpisodePubDate: Long? = null

    override fun getRefreshKey(state: PagingState<Int, EpisodeDto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDto> {
        val page: Int = params.key ?: 1

        Log.d("PagingSource", page.toString())
        return try {
            val response = api.getPodcastDetails(podcastId, nextEpisodePubDate)
            nextEpisodePubDate = response.nextEpisodePubDate
            if(page == 1) {
                podcastDetailsData(response)
            }
            val nextKey = if(response.episodes.size < 10) null else page + 1
            val prevKey = if(page == 1) null else page - 1
            LoadResult.Page(response.episodes, prevKey, nextKey)
        } catch (e: Exception) {
            Log.d("PagingSource", e.stackTraceToString())
            LoadResult.Error(e)
        }
    }

}