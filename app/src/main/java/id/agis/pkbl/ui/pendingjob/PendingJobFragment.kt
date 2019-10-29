package id.agis.pkbl.ui.pendingjob

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.agis.pkbl.R
import id.agis.pkbl.ui.pendingjob.binalingkungan.BinaLingkunganFragment
import id.agis.pkbl.ui.pendingjob.csr.CSRFragment
import id.agis.pkbl.ui.pendingjob.kemitraan.ProgramKemitraanFragment
import kotlinx.android.synthetic.main.fragment_pending_job.*

class PendingJobFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pending_job, container, false)
    }

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