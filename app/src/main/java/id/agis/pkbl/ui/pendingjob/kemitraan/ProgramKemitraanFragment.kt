package id.agis.pkbl.ui.pendingjob.kemitraan


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import id.agis.pkbl.R
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.model.Pemohon
import id.agis.pkbl.model.Pengajuan
import id.agis.pkbl.ui.pendingjob.PendingJobFragmentDirections
import kotlinx.android.synthetic.main.fragment_program_kemitraan.*

/**
 * A simple [Fragment] subclass.
 */
class ProgramKemitraanFragment : Fragment() {
    private lateinit var viewModel: ProgramKemitraanViewModel
    val listPenugasan = mutableListOf<Pengajuan>()
    val listPenilaian = mutableListOf<Pengajuan>()
    val listPersetujuan = mutableListOf<Pengajuan>()
    val listPencairan = mutableListOf<Pengajuan>()
    val listSelesai = mutableListOf<Pengajuan>()
    val listDitolak = mutableListOf<Pengajuan>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_program_kemitraan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = context!!.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE).getString(Constant.USER_NAME, "")

        viewModel = ViewModelProviders.of(this).get(ProgramKemitraanViewModel::class.java)
        viewModel.getPenugasan()
        viewModel.listPengajuan.observe(this, Observer {
            listPenugasan.clear()
            listPenilaian.clear()
            listPersetujuan.clear()
            listPencairan.clear()
            listSelesai.clear()
            listDitolak.clear()

            listPenugasan.addAll(it.filter { it.status == "Penugasan" && it.surveyor ==  user })
            listPenilaian.addAll(it.filter { it.status == "Penilaian" })
            listPersetujuan.addAll(it.filter { it.status == "Persetujuan" })
            listPencairan.addAll(it.filter { it.status == "Pencairan" })
            listSelesai.addAll(it.filter { it.status == "Selesai" })
            listDitolak.addAll(it.filter { it.status == "Ditolak" })

            println(listPenugasan.size.toString() + "aaaaaaaaaaaaaaaaaa")

            tv_penugasan.text = resources.getString(R.string.penugasan_survey, listPenugasan.size)
            tv_penilaian.text = resources.getString(R.string.penilaian, listPenilaian.size)
            tv_persetujuan.text = resources.getString(R.string.persetujuan, listPersetujuan.size)
            tv_pencairan.text = resources.getString(R.string.pencairan, listPencairan.size)
            tv_selesai.text = resources.getString(R.string.selesai, listPencairan.size)
            tv_ditolak.text = resources.getString(R.string.ditolak, listDitolak.size)

        })

        val title = "Program Kemitraan"
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
            navigate("Pencairan $title", listPencairan,  "pencairan")
        }
        card_selesai.setOnClickListener {
            navigate("Pencairan $title", listSelesai,  "selesai")
        }
        card_ditolak.setOnClickListener {
            navigate("Ditolak $title", listDitolak,  "ditolak")
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
