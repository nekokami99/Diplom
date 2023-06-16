package com.example.energy_statistics.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.energy_statistics.R
import com.example.energy_statistics.data.ChartResponse
import com.example.energy_statistics.data.ElectricUsed
import com.example.energy_statistics.data.Temperature
import com.example.energy_statistics.data.WaterUsed
import com.example.energy_statistics.databinding.FragmentChartBinding
import com.example.energy_statistics.ui.activity.home.HomeViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

class ChartFragment : Fragment() {

    private lateinit var binding: FragmentChartBinding
    private val viewmodel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObserve()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_chart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnClick()
    }

    private fun setupObserve() {
        obsserveDataChart()
        obsserveMessage()
    }

    private fun obsserveMessage() {
        viewmodel.message.observe(this) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun obsserveDataChart() {
        viewmodel.chartToday.observe(this) {
            setDataChart(it)
        }
        viewmodel.chartYesterday.observe(this) {
            setDataChart(it)
        }
    }

    private fun setupOnClick() {
        binding.btnToday.setOnClickListener {
            binding.btnToday.setBackgroundResource(R.drawable.bg_btn_select_chart)
            binding.btnYesterDay.setBackgroundResource(R.drawable.bg_btn_unselect_chart)
            viewmodel.getChartToDay()
        }

        binding.btnYesterDay.setOnClickListener {
            binding.btnYesterDay.setBackgroundResource(R.drawable.bg_btn_select_chart)
            binding.btnToday.setBackgroundResource(R.drawable.bg_btn_unselect_chart)
            viewmodel.getChartYesterday()
        }
    }

    private fun setDataChart(dataChart: ChartResponse) {
        dataChart.electricUsed?.let { setUpChartElectric(it) }
        dataChart.waterUsed?.let { setUpChartWater(it) }
        dataChart.temperature?.let { setUpChartTemp(it) }
    }

    private fun setupChart(chart: BarChart) {
        chart.description.isEnabled = false

        chart.setMaxVisibleValueCount(60)

        chart.setPinchZoom(false)

        chart.setDrawBarShadow(false)
        chart.setDrawGridBackground(false)

        val xAxis: XAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        chart.axisLeft.setDrawGridLines(false)
        chart.axisLeft.setDrawAxisLine(false)
        chart.axisRight.setDrawAxisLine(false)
        chart.axisRight.setDrawLabels(false)

        chart.animateY(1500)

        chart.legend.isEnabled = false
    }

    private fun setUpChartElectric(electricUsed: ElectricUsed) {
        setupChart(binding.chartElectric)
        setDataChart(binding.chartElectric, electricUsed)
        binding.txtElectric.text = "Electric used: ${electricUsed.total}kW"
    }

    private fun setUpChartWater(waterUsed: WaterUsed) {
        setupChart(binding.chartWater)
        setDataChart(binding.chartWater, waterUsed)
        binding.txtWater.text = "Water used: ${waterUsed.total}kW"
    }

    private fun setUpChartTemp(temperature: Temperature) {
        setupChart(binding.chartTemp)
        setDataChart(binding.chartTemp, temperature)
        binding.txtTemp.text = "Temperature: ${temperature.total}°C"
    }

    private fun setDataChart(chart: BarChart, dataChart: Any) {
        val values = ArrayList<BarEntry>()
        when (dataChart) {
            is ElectricUsed -> {
                dataChart.hours?.let {
                    for (i in dataChart.hours!!) {
                        values.add(BarEntry(i.hourTitle?.toFloat() ?: 0f, i.total?.toFloat() ?: 0f))
                    }
                }
            }

            is WaterUsed -> {
                dataChart.hours?.let {
                    for (i in dataChart.hours!!) {
                        values.add(BarEntry(i.hourTitle?.toFloat() ?: 0f, i.total?.toFloat() ?: 0f))
                    }
                }
            }

            is Temperature -> {
                dataChart.hours?.let {
                    for (i in dataChart.hours!!) {
                        values.add(BarEntry(i.hourTitle?.toFloat() ?: 0f, i.total?.toFloat() ?: 0f))
                    }
                }
            }
        }


        val set1: BarDataSet

        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart.data.barWidth = 0.25f
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {

            set1 = BarDataSet(values, "Data Set")
            set1.setColors(Color.rgb(91, 155, 213))
            set1.setDrawValues(false)

            val dataSets = java.util.ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets).apply { barWidth = 0.25f }
            chart.data = data
            chart.setFitBars(true)
        }
        chart.invalidate()
    }

}