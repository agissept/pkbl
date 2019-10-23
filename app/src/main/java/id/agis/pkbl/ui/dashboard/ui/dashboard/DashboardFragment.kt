package id.agis.pkbl.ui.dashboard.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import id.agis.pkbl.R
import kotlinx.android.synthetic.main.fragment_home.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carouselView.setImageListener { _, imageView ->
            Glide.with(this).load("https://via.placeholder.com/300x300.png").into(imageView)
        }
        carouselView.pageCount = 5


    }
}