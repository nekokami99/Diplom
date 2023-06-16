package com.example.energy_statistics.ui.activity.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.energy_statistics.R
import com.example.energy_statistics.ui.fragment.ChartFragment
import com.example.energy_statistics.databinding.ActivityHomeBinding
import com.example.energy_statistics.ui.fragment.AccountFragment
import com.example.energy_statistics.ui.fragment.HistoryFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chartFragment = ChartFragment()
        val historyFragment = HistoryFragment()
        val accFragment = AccountFragment()

        setCurrentFragment(chartFragment)

        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener  {
            when (it.itemId) {
                R.id.chart -> setCurrentFragment(chartFragment)
                R.id.history -> setCurrentFragment(historyFragment)
                R.id.account -> setCurrentFragment(accFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

}