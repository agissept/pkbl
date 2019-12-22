package id.agis.pkbl.ui.dashboard


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import id.agis.pkbl.R
import id.agis.pkbl.ui.dashboard.angsuran.AngsuranFragment
import id.agis.pkbl.ui.dashboard.perbulan.PerBulanFragment
import id.agis.pkbl.ui.dashboard.persektor.PerSektorFragment
import id.agis.pkbl.ui.dashboard.perwilayah.perpropinsi.PerPropinsiFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {
    private var backdropShown = false
    private lateinit var menu: Menu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadLayout(PerSektorFragment())

        (activity as AppCompatActivity).appbar?.elevation = 0f

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab_layout.selectedTabPosition) {
                    0 -> {
                        loadLayout(PerSektorFragment())
                    }
                    1 -> {
                        loadLayout(PerPropinsiFragment())
                    }
                    2 -> {
                        loadLayout(PerBulanFragment())
                    }
                    3 -> {
                        loadLayout(AngsuranFragment())
                    }
                }
            }

        })
    }

    private fun loadLayout(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dashboard, menu)
        this.menu = menu
        val item = menu.findItem(R.id.action_settings)
        item.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                showBackdrop()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showBackdrop() {
        val animatorSet = AnimatorSet()

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

        backdropShown = !backdropShown

        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        val translateY = backdrop.height

        val animator = ObjectAnimator.ofFloat(
            product_grid,
            "translationY",
            (if (backdropShown) translateY else 0).toFloat()
        )
        animator.duration = 500
        animator.interpolator = AccelerateDecelerateInterpolator()

        animatorSet.play(animator)
        animator.start()

        updateIcon()
    }

    private fun updateIcon() {
            if (backdropShown) {
                menu.findItem(R.id.filter).icon = context?.getDrawable(R.drawable.ic_close)
            } else {
                menu.findItem(R.id.filter).icon = context?.getDrawable(R.drawable.ic_filter)
            }
    }

}
