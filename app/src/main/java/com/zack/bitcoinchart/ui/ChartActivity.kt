package com.zack.bitcoinchart.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
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
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ChartActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: BitcoinChartViewModelFactory
    lateinit var viewModel: BitcoinChartViewModel
    lateinit var binding: ActivityChartBinding
    private lateinit var chart: LineChart
    protected lateinit var progressDialog: ProgressDialog


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chart)
        chart = binding.chart
        progressDialog    = ProgressDialog(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BitcoinChartViewModel::class.java)
        fetchBitcoinPrice()
    }

    private fun displayError(it: Throwable) {
        hideProgressDialog()
    }

    private fun showLoading() {
        if (!progressDialog.isShowing()) {
            progressDialog.setCancelable(false)
            progressDialog.setIndeterminate(false)
            progressDialog.setMessage(getString(R.string.progress_message))
            progressDialog.show()
        }
    }
    fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    @SuppressLint("CheckResult")
    fun  fetchBitcoinPrice() {
        showLoading()
        viewModel.fetchChartData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::populateLineChart, this::displayError)

    }
    private fun populateLineChart(chartData: ChartData) {
        hideProgressDialog()
        val values = chartData.values
        val entries = ArrayList<Entry>()
        values.iterator().forEach {
            entries.add(Entry(it.x, it.y))
        }
        val dataSet = LineDataSet(entries, "Bitcoin price");
        dataSet.setColor(Color.BLUE)
        dataSet.setValueTextColor(Color.RED)
        val lineData = LineData(dataSet)
        chart.data = lineData
        setUpChart()
        chart.invalidate()
    }

    private fun setUpChart() {
        val description = Description()
        description.text = getString(R.string.description)
        chart.isDragEnabled = true
        chart.isScaleXEnabled = true
        chart.isScaleYEnabled = true
        chart.isDoubleTapToZoomEnabled = true
        chart.setPinchZoom(true)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.valueFormatter = XValuesFormatter()
        chart.description = description
    }
}