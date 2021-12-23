package com.jackie.paging3demo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.rxjava2.observable
import com.jackie.paging3demo.logic.source.ArticlesPagingSource
import com.jackie.paging3demo.logic.source.RxArticlesPagingSource

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel == mcl"
    }

    val flow = Pager(
        PagingConfig(pageSize = 20, initialLoadSize = 20)
    ) {
        ArticlesPagingSource()
    }
        .flow
        .cachedIn(viewModelScope)

    val observable = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20),
        pagingSourceFactory = {
            RxArticlesPagingSource()
        }
    ).observable
}