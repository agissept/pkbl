package id.agis.pkbl.util

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.NumberFormat
import java.util.*

class CustomCurrencyFormatter: ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
        return formatter.format(value).toString()
    }
}