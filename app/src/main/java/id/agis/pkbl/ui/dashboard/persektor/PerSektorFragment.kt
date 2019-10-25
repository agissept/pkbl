package id.agis.pkbl.ui.dashboard.persektor

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import id.agis.pkbl.R
import kotlinx.android.synthetic.main.fragment_per_sektor.*
import java.util.*

class PerSektorFragment : Fragment(), OnChartValueSelectedListener {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_per_sektor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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


        // add a selection listener
        pie_chart.setOnChartValueSelectedListener(this)

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

        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.apply {
            add(PieEntry(4f, "Industri"))
            add(PieEntry(31f, "Perdagangan"))
            add(PieEntry(10f, "Pertanian"))
            add(PieEntry(11f, "Peternakan"))
            add(PieEntry(1f, "Perkebunan"))
            add(PieEntry(4f, "Pelabuhan"))
            add(PieEntry(13f, "Perikanan"))
            add(PieEntry(30f, "Jasa"))
            add(PieEntry(0f, "Usaha Lainnya"))
            add(PieEntry(0f, "Kerjasama Lainnya"))
        }


        val dataSet = PieDataSet(entries, "Election Results").apply {
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


    override fun onValueSelected(e: Entry?, h: Highlight) {

        if (e == null)
            return
        Log.i(
            "VAL SELECTED",
            "Value: " + e.y + ", xIndex: " + e.x
                    + ", DataSet index: " + h.dataSetIndex
        )
    }

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected")
    }

}
