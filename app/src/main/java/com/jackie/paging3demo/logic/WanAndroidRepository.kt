package com.jackie.paging3demo.logic

import com.jackie.paging3demo.logic.bean.ArticlesResponse
import com.jackie.paging3demo.logic.network.NetworkManager
import com.jackie.paging3demo.logic.network.request.WanAndroidService
import io.reactivex.Single

/**
 * @User: machenglong
 * @Date: 2021/12/22
 * @Describe:
 */
object WanAndroidRepository {

    suspend fun getFrontPageArticles(pageIndex: Int): ArticlesResponse {
        val wanAndroidService = NetworkManager.createService<WanAndroidService>()
        return wanAndroidService.getFrontPageArticles(pageIndex)
    }

    fun getFrontPageArticlesByRx(pageIndex: Int): Single<ArticlesResponse> {
        val wanAndroidService = NetworkManager.createServiceByRx<WanAndroidService>()
        return wanAndroidService.getFrontPageArticlesByRx(pageIndex)
    }
}