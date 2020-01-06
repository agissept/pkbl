package id.agis.pkbl.ui.pendingjob.selesai


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.agis.pkbl.R
import id.agis.pkbl.ui.pendingjob.ViewPagerAdapter
import id.agis.pkbl.ui.pendingjob.selesai.listselesai.ListSelesaiFragment
import kotlinx.android.synthetic.main.fragment_pending_job.*

/**
 * A simple [Fragment] subclass.
 */
class SelesaiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_job, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ListSelesaiFragment(), "Diterima")
        adapter.addFragment(ListSelesaiFragment(), "Ditolak")
        tab_layout.setupWithViewPager(view_pager)
        view_pager.adapter = adapter
    }


}
