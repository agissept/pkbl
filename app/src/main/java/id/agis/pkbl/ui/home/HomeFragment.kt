package id.agis.pkbl.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import id.agis.pkbl.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carouselView.apply {
            setImageListener { _, imageView ->
                Glide.with(this).load("https://via.placeholder.com/300x300.png").into(imageView)
            }
            pageCount = 5
        }

        btn_dashboard.setOnClickListener {
            startFragment(R.id.nav_dashboard)
        }
        btn_pending_job.setOnClickListener {
            startFragment(R.id.nav_pending_job)
        }
        btn_status.setOnClickListener {
            startFragment(R.id.nav_status)
        }
        btn_kredit.setOnClickListener {
            startFragment(R.id.nav_kredit)
        }
        btn_dokumen.setOnClickListener {
            startFragment(R.id.nav_dokumen)
        }
        btn_info.setOnClickListener {
            startFragment(R.id.nav_info)
        }
    }

    private fun startFragment(id: Int){
        findNavController().navigate(id)
    }
}