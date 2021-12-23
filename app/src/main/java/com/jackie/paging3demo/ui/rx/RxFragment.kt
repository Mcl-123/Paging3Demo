package com.jackie.paging3demo.ui.rx

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackie.paging3demo.R
import com.jackie.paging3demo.databinding.FragmentRxBinding
import com.jackie.paging3demo.ui.articles.ArticleComparator
import com.jackie.paging3demo.ui.articles.ArticlesAdapter
import com.jackie.paging3demo.ui.main.MainViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class RxFragment : Fragment() {

    companion object {
        private const val TAG = "RxFragment == mcl"
    }

    private lateinit var binding: FragmentRxBinding
    private var disposable: Disposable? = null
    private val compositeDisposable = CompositeDisposable()
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rx, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated => entry")
        val adapter = ArticlesAdapter(ArticleComparator)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.ivLoading.visibility = View.GONE
                    binding.ivError.visibility = View.GONE

                    stopLoading()
                }
                is LoadState.Error -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.ivLoading.visibility = View.GONE
                    binding.ivError.visibility = View.VISIBLE

                    stopLoading()
                }
                is LoadState.Loading -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.ivLoading.visibility = View.VISIBLE
                    binding.ivError.visibility = View.GONE

                    startLoading()
                }
            }
        }
        val disposable = viewModel.observable.subscribe { adapter.submitData(lifecycle, it) }
        compositeDisposable.add(disposable)
    }

    private fun startLoading() {
        disposable =
            Observable.interval(0, 1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    ObjectAnimator.ofFloat(binding.ivLoading, "rotation", 0f, 360f).apply {
                        duration = 1500
                    }
                        .start()
                }
    }

    private fun stopLoading() {
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}