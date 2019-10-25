package id.agis.pkbl.ui.dashboard

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class CustomViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var height = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            child.measure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )

            val h = child.measuredHeight

            if (h > height) height = h
        }

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
    }
}


