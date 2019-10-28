package id.agis.pkbl.ui.statuspermohonan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.agis.pkbl.R
import kotlinx.android.synthetic.main.fragment_status_permohonan.*


class StatusPermohonanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_status_permohonan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_tampilkan.setOnClickListener {
            val pemohon = ed_pemohon.text.toString()
            val institusi = ed_institusi.text.toString()
            if (pemohon == "" && institusi == "") {
                ed_institusi.error = "Salah satu field harus diisi"
                ed_pemohon.error = "Salah satu field harus diisi"
            } else {
                val action =
                    StatusPermohonanFragmentDirections.actionNavStatusToNavListStatusPemohonan(
                        pemohon,
                        institusi,
                        cb_kemitraan.isChecked,
                        cb_bina_lingkungan.isChecked,
                        cb_bina_wilayah.isChecked
                    )
                findNavController().navigate(action)
            }
        }

    }

}