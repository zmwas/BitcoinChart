package com.zack.bitcoinchart.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.zack.bitcoinchart.R
import com.zack.bitcoinchart.databinding.ActivityChartBinding
import com.zack.bitcoinchart.viewmodel.BitcoinChartViewModel
import com.zack.bitcoinchart.viewmodel.BitcoinChartViewModelFactory
import com.zack.data.model.ChartData
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ChartActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: BitcoinChartViewModelFactory
    private lateinit var viewModel: BitcoinChartViewModel
    private lateinit var binding: ActivityChartBinding
    private lateinit var priceChart: LineChart
    lateinit var timeSpan: String
    lateinit var rollingAverage: String

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chart)
        priceChart = binding.priceChart
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BitcoinChartViewModel::class.java)
        timeSpan = "2weeks"
        rollingAverage = "8hours"
        fetchBitcoinPrice(timeSpan, rollingAverage)
        setUpSpinners()
    }

    private fun displayError(it: Throwable) {
        hideLoading()
        val builder = AlertDialog.Builder(this)
        builder.setMessage(it.message)
            .setCancelable(true)
            .setPositiveButton(android.R.string.ok) { dialog, d ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun setUpSpinners() {
        binding.timespanSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                timeSpan = adapterView!!.getItemAtPosition(position).toString().replace(" ", "")
                fetchBitcoinPrice(timeSpan, rollingAverage)
            }
        }

        binding.rollingAverageSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                rollingAverage = adapterView!!.getItemAtPosition(position).toString().replace(" ", "")
                fetchBitcoinPrice(timeSpan, rollingAverage)
            }
        }
    }

    private fun showLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
        binding.priceChart.visibility = View.GONE
        binding.filter.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.loadingLayout.visibility = View.GONE
        binding.priceChart.visibility = View.VISIBLE
        binding.filter.visibility = View.VISIBLE

    }

    @SuppressLint("CheckResult")
    fun fetchBitcoinPrice(timeSpan: String, rollingAverage: String) {
        showLoading()
        viewModel.fetchChartData(timeSpan, rollingAverage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::populateLineChart, this::displayError)
    }

    private fun populateLineChart(chartData: ChartData) {
        hideLoading()
        val values = chartData.values
        val entries = ArrayList<Entry>()
        values.iterator().forEach {
            entries.add(Entry(it.x, it.y))
        }
        val dataSet = LineDataSet(entries, getString(R.string.chart_label));
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.RED
        val lineData = LineData(dataSet)
        priceChart.data = lineData
        setUpChart()
        priceChart.invalidate()
    }

    private fun setUpChart() {
        val description = Description()
        description.text = getString(R.string.description)
        priceChart.isDragEnabled = true
        priceChart.isScaleXEnabled = true
        priceChart.isScaleYEnabled = true
        priceChart.isDoubleTapToZoomEnabled = true
        priceChart.setPinchZoom(true)
        priceChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        priceChart.xAxis.valueFormatter = XValuesFormatter()
        priceChart.description = description
    }
}