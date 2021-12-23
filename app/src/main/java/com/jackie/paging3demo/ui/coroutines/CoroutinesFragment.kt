package com.jackie.paging3demo.ui.coroutines

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackie.paging3demo.R
import com.jackie.paging3demo.databinding.FragmentCoroutinesBinding
import com.jackie.paging3demo.ui.articles.ArticleComparator
import com.jackie.paging3demo.ui.articles.ArticlesAdapter
import com.jackie.paging3demo.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class CoroutinesFragment : Fragment() {

    companion object {
        private const val TAG = "CoroutinesFragment == mcl"
    }

    private lateinit var binding: FragmentCoroutinesBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var loadingTimer: Timer? = null
    private val startLoadingHandler = Handler(Looper.getMainLooper()) {
        ObjectAnimator.ofFloat(binding.ivLoading, "rotation", 0f, 360f)
            .apply {
                duration = 1500
            }.start()
        false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coroutines, container, false)
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
        binding.ivError.setOnClickListener {
            Log.d(TAG, "onActivityCreated => onClick")
            adapter.retry()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.flow.collectLatest {
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun startLoading() {
        loadingTimer = Timer()
        loadingTimer!!.schedule(object : TimerTask() {
            override fun run() {
                startLoadingHandler.sendEmptyMessage(0)
            }
        }, 0, 1500)
    }

    private fun stopLoading() {
        loadingTimer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingTimer?.cancel()
        Log.d(TAG, "onDestroy => entry")
    }
}