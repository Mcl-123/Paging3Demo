package com.jackie.paging3demo.logic.network.request

import com.jackie.paging3demo.logic.bean.ArticlesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @User: machenglong
 * @Date: 2021/12/22
 * @Describe:
 */
interface WanAndroidService {
    /**
     * 获取首页文章列表
     * https://www.wanandroid.com/article/list/1/json
     */
    @GET("/article/list/{pageIndex}/json")
    suspend fun getFrontPageArticles(@Path("pageIndex") pageIndex: Int): ArticlesResponse

    @GET("/article/list/{pageIndex}/json")
    fun getFrontPageArticlesByRx(@Path("pageIndex") pageIndex: Int): Single<ArticlesResponse>
}