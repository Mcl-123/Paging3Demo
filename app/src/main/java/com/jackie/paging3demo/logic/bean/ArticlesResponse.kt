package com.jackie.paging3demo.logic.bean

/**
 * @User: machenglong
 * @Date: 2021/12/22
 * @Describe:
 */

data class ArticlesResponse(
    val data: ArticlesData,
    val errorCode: Int,
    val errorMsg: String,
)

data class ArticlesData(
    val curPage: Int,
    val datas: List<Article>,
    val offset: Int,
    val isOver: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
)

data class Article(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val isCanEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val isCollect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val isFresh: Boolean,
    val host: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
)
    
