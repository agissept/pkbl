package id.agis.pkbl.ui.dashboard.perwilayah


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.chip.Chip
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import id.agis.pkbl.util.CustomMarkerView
import id.agis.pkbl.util.CustomPercentFormatter
import kotlinx.android.synthetic.main.backdrop.*
import kotlinx.android.synthetic.main.fragment_per_wilayah.*
import kotlinx.android.synthetic.main.fragment_per_wilayah.progress_bar
import kotlinx.android.synthetic.main.fragment_per_wilayah.recycler_view
import kotlinx.android.synthetic.main.fragment_per_wilayah.tv_nama
import org.jetbrains.anko.collections.forEachWithIndex

/**
 * A simple [Fragment] subclass.
 */
class PerWilayahViewModel : Fragment() {

    lateinit var adapter: PerWilayahAdapter
    val listPengajuan = mutableListOf<Pengajuan>()
    val values1 = ArrayList<BarEntry>()
    val values2 = ArrayList<BarEntry>()
    var jumlahDana = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_per_wilayah, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appCompatActivity = activity as AppCompatActivity
        val bulan = appCompatActivity.sp_bulan.selectedItem.toString()
        val tahun = appCompatActivity.sp_tahun.selectedItem.toString()
        val chipId = appCompatActivity.chip_group.checkedChipId
        val type = appCompatActivity.findViewById<Chip>(chipId).text.toString()

        tv_nama.text = resources.getString(R.string.title_chart_dashboard, type, bulan, tahun)

        adapter =
            PerWilayahAdapter(listPengajuan)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter

        val viewModel = ViewModelProviders.of(this).get(PerPropinsiViewModel::class.java)

        viewModel.getPengajuan("provinsi", bulan, tahun, type)
        viewModel.dataPengajuan.observe(this, androidx.lifecycle.Observer {
            listPengajuan.clear()
            listPengajuan.addAll(it)

            jumlahDana = listPengajuan.sumBy { pengajuan ->
                pengajuan.dana.toInt()
            }

            listPengajuan.forEachWithIndex { i, pengajuan ->
                values1.add(BarEntry(i.toFloat(), pengajuan.dana.toFloat()))
                val data = arrayOf("percent", jumlahDana.toFloat())
                values2.add(BarEntry(0f, pengajuan.dana.toFloat(), data))

            }

            adapter.notifyDataSetChanged()
            if(listPengajuan.size > 0){
                setData()
                progress_bar.visibility = View.INVISIBLE
            }

        })

        chart.apply {
            description.isEnabled = false
            // scaling can now only be done on x- and y-axis separately
            setPinchZoom(false)
            setDrawBarShadow(false)
            setDrawGridBackground(false)
            extraBottomOffset = 30f
        }

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        val mv = CustomMarkerView(context!!, R.layout.custom_marker_view).apply {
            chartView = chart // For bounds control
        }
        chart.marker = mv // Set the marker to the chart


        chart.axisLeft.apply {
            spaceTop = 35f
            axisMinimum = 0f // this replaces setStartAtZero(true)
            isEnabled = true
        }

        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false


    }


    fun setData() {
        chart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setAvoidFirstLastClipping(false)
            setDrawGridLines(false)
            setCenterAxisLabels(false)
            labelCount = listPengajuan.size
            axisMinimum = 0f
            granularity = 1f
            labelRotationAngle = -45f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val x: Int = if (listPengajuan.size > 0) {
                        value.toInt() % listPengajuan.size
                    } else {
                        0
                    }

                    return if (x >= 0 && x < listPengajuan.size) {
                        listPengajuan[x].provinsi
                    } else {
                        ""
                    }
                }
            }

        }


        val groupSpace = 0.12f
        val spaceBetweenBarInGroup = 0f // x4 DataSet
        val barWidth = 0.4f // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        val groupCount = listPengajuan.size
        val startYear = 0

        val totalValue = jumlahDana.toFloat()


        val set1: BarDataSet
        val set2: BarDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) run {

            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set2 = chart.data.getDataSetByIndex(1) as BarDataSet

            set1.values = values1
            set2.values = values2
            set2.valueFormatter = CustomPercentFormatter(totalValue)

            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()

        } else run {
            // create 2 DataSets
            set1 = BarDataSet(values1, "Rekap 2018")
            set1.color = Color.rgb(3, 155, 229)

            set2 = BarDataSet(values2, "Realisasi 1 Jan - 31 Desember 2018")
            set2.color = Color.rgb(245, 124, 0)
            set2.valueFormatter = CustomPercentFormatter(totalValue)

            val data = BarData(set1, set2)


            chart.data = data
        }

        // specify the width each bar should have
        chart.barData.barWidth = barWidth

        // restrict the x-axis range
        chart.xAxis.axisMinimum = startYear.toFloat()

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        chart.xAxis.axisMaximum =
            startYear + chart.barData.getGroupWidth(groupSpace, spaceBetweenBarInGroup) * groupCount
        chart.groupBars(startYear.toFloat(), groupSpace, spaceBetweenBarInGroup)
        chart.invalidate()
    }

}
