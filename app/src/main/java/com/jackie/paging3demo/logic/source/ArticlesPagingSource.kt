package com.jackie.paging3demo.logic.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jackie.paging3demo.logic.WanAndroidRepository
import com.jackie.paging3demo.logic.bean.Article

/**
 * @User: machenglong
 * @Date: 2021/12/22
 * @Describe:
 */
class ArticlesPagingSource() : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val pageNumber = params.key ?: 1
            val response = WanAndroidRepository.getFrontPageArticles(pageNumber)
            LoadResult.Page(
                data = response.data.datas,
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (response.data.datas.isNullOrEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}