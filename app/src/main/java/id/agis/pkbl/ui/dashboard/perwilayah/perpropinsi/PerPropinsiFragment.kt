package id.agis.pkbl.ui.dashboard.perwilayah.perpropinsi


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import id.agis.pkbl.R
import id.agis.pkbl.ui.dashboard.perwilayah.perkota.PerKotaFragment
import id.agis.pkbl.util.CustomMarkerView
import id.agis.pkbl.util.CustomPercentFormatter
import kotlinx.android.synthetic.main.fragment_per_propinsi.*

/**
 * A simple [Fragment] subclass.
 */
class PerPropinsiFragment : Fragment(), OnChartValueSelectedListener {

    val judul = arrayListOf(
        "Sumatera Selatan",
        "Sumatera Barat",
        "Lampung",
        "Jakarta",
        "Jawa Barat",
        "Sumatera Barat",
        "Jawa Timur"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_per_propinsi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chart.apply {
            description.isEnabled = false
            // scaling can now only be done on x- and y-axis separately
            setPinchZoom(false)
            setDrawBarShadow(false)
            setDrawGridBackground(false)
            extraBottomOffset = 30f
        }
        chart.setOnChartValueSelectedListener(this)

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        val mv = CustomMarkerView(context!!, R.layout.custom_marker_view).apply {
            chartView = chart // For bounds control
        }
        chart.marker = mv // Set the marker to the chart


        chart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setAvoidFirstLastClipping(false)
            setDrawGridLines(false)
            setCenterAxisLabels(false)
            labelCount = 7
            axisMinimum = 0f
            granularity = 1f
            labelRotationAngle = -45f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val x = value.toInt() % judul.size
                    return if (x >= 0 && x < judul.size) {
                        judul[x]
                    } else {
                        ""
                    }
                }
            }

        }

        chart.axisLeft.apply {
            spaceTop = 35f
            axisMinimum = 0f // this replaces setStartAtZero(true)
            isEnabled = true
        }

        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false

        val groupSpace = 0.12f
        val spaceBetweenBarInGroup = 0f // x4 DataSet
        val barWidth = 0.4f // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        val groupCount = 7
        val startYear = 0
        val endYear = 7

        val totalValue = 1000000f

        val values1 = ArrayList<BarEntry>()
        val values2 = ArrayList<BarEntry>()

        val data = arrayOf("percent", totalValue)

        values2.add(BarEntry(0f, 500000f, data))
        values2.add(BarEntry(0f, 100000f, data))
        values2.add(BarEntry(0f, 50000f, data))
        values2.add(BarEntry(0f, 50000f, data))
        values2.add(BarEntry(0f, 200000f, data))
        values2.add(BarEntry(0f, 80000f, data))
        values2.add(BarEntry(0f, 20000f, data))

        val randomMultiplier = 10 * 100000f

        for (i in startYear until endYear) {
            values1.add(BarEntry(i.toFloat(), (Math.random() * randomMultiplier).toFloat()))
        }

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

    override fun onNothingSelected() {}

    override fun onValueSelected(e: Entry?, h: Highlight?) {

        val wilayah = e?.let { chart.xAxis.valueFormatter.getFormattedValue(it.x) }
        fragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.container, PerKotaFragment())
            ?.commit()
    }

}
