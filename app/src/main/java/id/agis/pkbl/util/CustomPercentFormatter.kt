package id.agis.pkbl.util

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

class CustomPercentFormatter(private val totalValue: Float) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val formatter = DecimalFormat("###,###,##0.0")
        val newValue = value / totalValue * 100
        return formatter.format(newValue).toString() + "%"
    }
}