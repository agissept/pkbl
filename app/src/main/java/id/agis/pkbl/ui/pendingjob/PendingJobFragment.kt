package id.agis.pkbl.ui.pendingjob

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import id.agis.pkbl.R
import id.agis.pkbl.ui.pendingjob.kemitraan.ProgramKemitraanFragment
import kotlinx.android.synthetic.main.fragment_pending_job.*
import id.agis.pkbl.ui.pendingjob.binalingkungan.BinaLingkunganFragment
import id.agis.pkbl.ui.pendingjob.csr.CSRFragment


class PendingJobFragment : Fragment(R.layout.fragment_pending_job) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ProgramKemitraanFragment(), "Program Kemitraan")
        adapter.addFragment(BinaLingkunganFragment(), "Bina Lingkungan")
        adapter.addFragment(CSRFragment(), "CSR")

        tab_layout.setupWithViewPager(view_pager)
        view_pager.adapter = adapter
    }
}