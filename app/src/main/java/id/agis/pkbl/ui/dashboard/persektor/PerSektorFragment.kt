package id.agis.pkbl.ui.dashboard.persektor

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.chip.Chip
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import kotlinx.android.synthetic.main.backdrop.*
import kotlinx.android.synthetic.main.fragment_per_sektor.*
import kotlin.collections.ArrayList

class PerSektorFragment : Fragment() {
    val listPengajuan = mutableListOf<Pengajuan>()
    val listPieEntry = ArrayList<PieEntry>()
    lateinit var adapter: PerSektorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_per_sektor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appCompatActivity = activity as AppCompatActivity
        val bulan = appCompatActivity.sp_bulan.selectedItem.toString()
        val tahun = appCompatActivity.sp_tahun.selectedItem.toString()
        val chipId = appCompatActivity.chip_group.checkedChipId
        val type = appCompatActivity.findViewById<Chip>(chipId).text.toString()

        tv_nama.text = resources.getString(R.string.title_chart_dashboard, type, bulan, tahun)

        adapter = PerSektorAdapter(listPengajuan)
        rv_file.layoutManager = LinearLayoutManager(context)
        rv_file.adapter = adapter

        val viewModel = ViewModelProviders.of(this).get(PerSektorViewModel::class.java)

        viewModel.getPengajuan("sektor", bulan, tahun, type)
        viewModel.dataPengajuan.observe(this, androidx.lifecycle.Observer {
            listPengajuan.clear()
            listPengajuan.addAll(it)

            val jumlahDana = listPengajuan.sumBy { pengajuan ->
                pengajuan.dana.toInt()
            }

            listPengajuan.forEach { pengajuan ->
                val percent = pengajuan.dana.toFloat() / jumlahDana * 100
                listPieEntry.add(PieEntry(percent, pengajuan.sektor))
            }

            adapter.notifyDataSetChanged()
            setData()
            progress_bar.visibility = View.INVISIBLE
            if (listPengajuan.size < 1) {
                tv_error.visibility = View.VISIBLE
                layout_chart.visibility = View.INVISIBLE
            } else {
                tv_error.visibility = View.INVISIBLE
                layout_chart.visibility = View.VISIBLE
            }
        })




        pie_chart.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            setExtraOffsets(20f, 0f, 20f, 0f)
            isDrawHoleEnabled = false
            holeRadius = 58f
            transparentCircleRadius = 61f
            setDrawCenterText(true)

            rotationAngle = 0f
            // enable rotation of the chart by touch
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
        }

        pie_chart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            orientation = Legend.LegendOrientation.VERTICAL
            setDrawInside(false)
            isEnabled = false
        }

        setData()

    }


    private fun setData() {
        val dataSet = PieDataSet(listPieEntry, "Election Results").apply {
            sliceSpace = 3f
            selectionShift = 5f
        }

        // add a lot of colors

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLinePart1Length = 0.2f
        dataSet.valueLinePart2Length = 0.4f
        //dataSet.setUsingSliceColorAsValueLineColor(true);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

        val data = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter())
            setValueTextSize(11f)
            setValueTextColor(Color.BLACK)
        }

        //        data.setValueTypeface(tf);
        pie_chart.data = data

        // undo all highlights
        pie_chart.highlightValues(null)

        pie_chart.invalidate()
    }
}
