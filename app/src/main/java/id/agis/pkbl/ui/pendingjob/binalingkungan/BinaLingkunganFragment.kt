package id.agis.pkbl.ui.pendingjob.binalingkungan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.agis.pkbl.R
import id.agis.pkbl.ui.pendingjob.PendingJobFragmentDirections
import kotlinx.android.synthetic.main.fragment_bina_lingkungan.*

/**
 * A simple [Fragment] subclass.
 */
class BinaLingkunganFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bina_lingkungan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = "Bina Lingkungan"
        card_penugasan.setOnClickListener {
            navigate("Penugasan $title")
        }
        card_penilaian.setOnClickListener {
            navigate("Penilaian $title")
        }
        card_persetujuan.setOnClickListener {
            navigate("Persetujuan $title")
        }
        card_pencairan.setOnClickListener {
            navigate("Pencairan $title")
        }
    }

    private fun navigate(title: String){
        val action = PendingJobFragmentDirections.actionNavPendingJobToNavListPemohon(title)
        findNavController().navigate(action)
    }


}
