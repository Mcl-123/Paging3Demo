package com.jackie.paging3demo.ui.articles

import androidx.recyclerview.widget.DiffUtil
import com.jackie.paging3demo.logic.bean.Article

/**
 * @User: machenglong
 * @Date: 2021/12/21
 * @Describe:
 */
object ArticleComparator : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}