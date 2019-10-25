package id.agis.pkbl.ui.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.agis.pkbl.R
import id.agis.pkbl.ui.dashboard.perbulan.PerBulanFragment
import id.agis.pkbl.ui.dashboard.persektor.PerSektorFragment
import id.agis.pkbl.util.TabAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlin.math.abs

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment(), View.OnTouchListener {

    private var downX = 0
    private var downY = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TabAdapter(fragmentManager!!).apply {
            addFragment(PerSektorFragment(), "Per Sektor")
            addFragment(PerBulanFragment(), "Per Wilayah")
            addFragment(PerBulanFragment(), "Per Bulan")
            addFragment(PerBulanFragment(), "Angsuran")
        }

        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }



    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val dragThreshold = 30

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.rawX.toInt()

                downY = event.rawY.toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                val distanceX = abs(event.rawX.toInt() - downX)

                val distanceY = abs(event.rawY.toInt() - downY)

                if (distanceY > distanceX && distanceY > dragThreshold) {
                    view_pager.parent.requestDisallowInterceptTouchEvent(false)

                    scroll_view.parent.requestDisallowInterceptTouchEvent(true)
                } else if (distanceX > distanceY && distanceX > dragThreshold) {
                    view_pager.parent.requestDisallowInterceptTouchEvent(true)

                    scroll_view.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {
                scroll_view.parent.requestDisallowInterceptTouchEvent(false)

                view_pager.parent.requestDisallowInterceptTouchEvent(false)
            }
        }

        return false
    }


}
