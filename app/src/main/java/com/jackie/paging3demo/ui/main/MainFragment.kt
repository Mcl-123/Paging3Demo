package com.jackie.paging3demo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jackie.paging3demo.R
import com.jackie.paging3demo.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        private const val TAG = "MainFragment == mcl"
    }

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        binding.btnCoroutines.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_coroutinesFragment)
        }
        binding.btnRx.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_rxFragment)
        }

        return binding.root
    }
}