package id.agis.pkbl.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import id.agis.pkbl.R
import java.text.DecimalFormat


@SuppressLint("ViewConstructor")
class CustomMarkerView(context: Context, layoutResource: Int) :
    MarkerView(context, layoutResource) {

    private val tvContent: TextView = findViewById(R.id.tvContent)

    // content (user-interface)
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val data = if (e?.data != null) {
            e.data as Array<*>
        } else {
            arrayOf("")
        }

        when {
            e is CandleEntry -> {

                val ce = e as CandleEntry?

                tvContent.text = Utils.formatNumber(ce!!.high, 0, true)
            }
            data[0] == "percent" -> {
                val formatter = DecimalFormat("###,###,##0.0")
                val newValue = e!!.y / data[1] as Float * 100
                tvContent.text = formatter.format(newValue).toString() + "%"
            }
            else -> tvContent.text = Utils.formatNumber(e!!.y, 0, true)
        }

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}
