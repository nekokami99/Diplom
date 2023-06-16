package com.example.energy_statistics.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.energy_statistics.data.HistoryResponse
import com.example.energy_statistics.databinding.FragmentHistoryBinding
import com.example.energy_statistics.ui.activity.home.HomeViewModel
import com.example.energy_statistics.ui.adapter.HistoryAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewmodel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    private val TAB_DAY = "Day"
    private val TAB_MONTH = "Month"

    private lateinit var adapter: HistoryAdapter
    private var tabSelect = TAB_DAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeHistory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun observeHistory() {
        viewmodel.historyDay.observe(this) {
            binding.srlHistory.isRefreshing = false
            if (tabSelect == TAB_DAY) {
                updateRecyclerHistory(it)
            }
        }
        viewmodel.historyMonth.observe(this) {
            binding.srlHistory.isRefreshing = false
            if (tabSelect == TAB_MONTH) {
                updateRecyclerHistory(it)
            }
        }
        viewmodel.success.observe(this) {
            binding.srlHistory.isRefreshing = false
        }
    }

    private fun updateRecyclerHistory(data: List<HistoryResponse>?) {
        if (data != null) {
            adapter.updateData(data)
        } else {
            adapter.updateData(listOf())
        }

    }

    private fun setupView() {
        adapter = HistoryAdapter(listOf())

        val tabDay = binding.tabLayout.newTab().setText(TAB_DAY)
        val tabMonth = binding.tabLayout.newTab().setText(TAB_MONTH)
        binding.tabLayout.addTab(tabDay)
        binding.tabLayout.addTab(tabMonth)
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    TAB_DAY -> {
                        tabSelect = TAB_DAY
                        updateRecyclerHistory(viewmodel.historyDay.value)
                    }

                    TAB_MONTH -> {
                        tabSelect = TAB_MONTH
                        updateRecyclerHistory(viewmodel.historyMonth.value)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.rvHistory.layoutManager = LinearLayoutManager(context)
        binding.rvHistory.adapter = adapter

        binding.srlHistory.setOnRefreshListener {
            when (tabSelect) {
                TAB_DAY -> {
                    viewmodel.getHistoryDay()
                }

                TAB_MONTH -> {
                    viewmodel.getHistoryMonth()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(tabSelect == TAB_MONTH) {
            val tabMonth = binding.tabLayout.getTabAt(1)
            binding.tabLayout.selectTab(tabMonth)
        }

    }
}