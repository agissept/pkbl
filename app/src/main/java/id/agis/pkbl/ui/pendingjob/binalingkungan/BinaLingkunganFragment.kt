package id.agis.pkbl.ui.pendingjob.binalingkungan

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import id.agis.pkbl.ui.pendingjob.PendingJobFragmentDirections
import id.agis.pkbl.ui.pendingjob.PendingJobViewModel
import kotlinx.android.synthetic.main.fragment_bina_lingkungan.card_pencairan
import kotlinx.android.synthetic.main.fragment_bina_lingkungan.card_penilaian
import kotlinx.android.synthetic.main.fragment_bina_lingkungan.card_penugasan
import kotlinx.android.synthetic.main.fragment_bina_lingkungan.card_persetujuan
import kotlinx.android.synthetic.main.fragment_type_pending_job.*
import id.agis.pkbl.constant.Constant

/**
 * A simple [Fragment] subclass.
 */
class BinaLingkunganFragment : Fragment(R.layout.fragment_type_pending_job) {
    private lateinit var viewModel: PendingJobViewModel
    private val listPenugasan = mutableListOf<Pengajuan>()
    private val listPenilaian = mutableListOf<Pengajuan>()
    private val listPersetujuan = mutableListOf<Pengajuan>()
    private val listPencairan = mutableListOf<Pengajuan>()
    private val listSelesai = mutableListOf<Pengajuan>()
    private val listDitolak = mutableListOf<Pengajuan>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = context!!.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE)
            .getString(Constant.USER_USERNAME, "")

        viewModel = ViewModelProviders.of(this).get(PendingJobViewModel::class.java)
        viewModel.getPenugasan("bina lingkungan")
        viewModel.listPengajuan.observe(this, Observer { list ->
            listPenugasan.clear()
            listPenilaian.clear()
            listPersetujuan.clear()
            listPencairan.clear()
            listSelesai.clear()
            listDitolak.clear()

            listPenugasan.addAll(list.filter { it.status == "Penugasan" && it.surveyor == user })
            listPenilaian.addAll(list.filter { it.status == "Penilaian" })
            listPersetujuan.addAll(list.filter { it.status == "Persetujuan" })
            listPencairan.addAll(list.filter { it.status == "Pencairan" })
            listSelesai.addAll(list.filter { it.status == "Selesai" })
            listDitolak.addAll(list.filter { it.status == "Ditolak" })

            tv_penugasan.text = resources.getString(R.string.penugasan_survey, listPenugasan.size)
            tv_penilaian.text = resources.getString(R.string.penilaian, listPenilaian.size)
            tv_persetujuan.text = resources.getString(R.string.persetujuan, listPersetujuan.size)
            tv_pencairan.text = resources.getString(R.string.pencairan, listPencairan.size)
            tv_selesai.text = resources.getString(R.string.selesai, listSelesai.size)
            tv_ditolak.text = resources.getString(R.string.ditolak, listDitolak.size)

            progress_bar.visibility = View.INVISIBLE
            layout_button.visibility = View.VISIBLE
        })

        val title = "Bina Lingkungan"
        card_penugasan.setOnClickListener {
            navigate("Penugasan $title", listPenugasan.toList(), "penugasan")
        }
        card_penilaian.setOnClickListener {
            navigate("Penilaian $title", listPenilaian, "penilaian")
        }
        card_persetujuan.setOnClickListener {
            navigate("Persetujuan $title", listPersetujuan, "persetujuan")
        }
        card_pencairan.setOnClickListener {
            navigate("Pencairan $title", listPencairan, "pencairan")
        }
        card_selesai.setOnClickListener {
            navigate("Pencairan $title", listSelesai, "selesai")
        }
        card_ditolak.setOnClickListener {
            navigate("Ditolak $title", listDitolak, "ditolak")
        }

    }

    private fun navigate(title: String, listPengajuan: List<Pengajuan>, source: String) {
        val action = PendingJobFragmentDirections.actionNavPendingJobToNavListPemohon(
            title,
            listPengajuan.toTypedArray(),
            source
        )
        findNavController().navigate(action)
    }


}
