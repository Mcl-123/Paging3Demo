package com.jackie.paging3demo.logic.source

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.jackie.paging3demo.logic.WanAndroidRepository
import com.jackie.paging3demo.logic.bean.Article
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @User: machenglong
 * @Date: 2021/12/23
 * @Describe:
 */
class RxArticlesPagingSource : RxPagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Article>> {
        val pageNumber = params.key ?: 1
        return WanAndroidRepository.getFrontPageArticlesByRx(pageNumber)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    it.data.datas,
                    if ((params.key ?: 1) > 1) (params.key ?: 1) - 1 else null,
                    if (it.data.datas.isEmpty()) null else (params.key ?: 1) + 1
                )
            }
        // 用 java 不会报错,kotlin 报错 TT
//            .onErrorReturn {
//                LoadResult.Error(it)
//            }
    }
}