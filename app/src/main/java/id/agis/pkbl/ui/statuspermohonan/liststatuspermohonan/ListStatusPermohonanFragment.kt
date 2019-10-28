package id.agis.pkbl.ui.statuspermohonan.liststatuspermohonan


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import kotlinx.android.synthetic.main.fragment_list_status_permohonan.*

/**
 * A simple [Fragment] subclass.
 */
class ListStatusPermohonanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_status_permohonan, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = arguments?.let { ListStatusPermohonanFragmentArgs.fromBundle(it) }

        val pemohon = argument?.pemohon
        val institusi = argument?.institusi
        val kemitraan = argument?.kemitraan
        val binaLingkungan = argument?.binaLingkungan
        val binaWilayahFragment = argument?.binaWilayah

        tv_title.text = "$pemohon, $institusi"

        val sizeIsNotEmpty = arrayListOf(true, false).random()

        if (sizeIsNotEmpty) {
            recycler_view.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ListStatusPermohonanAdapter(10)
            }
        } else {
            AlertDialog.Builder(context).apply {
                setMessage("Maaf Tidak ada permohonan atas nama \"$pemohon\" dan atau Usaha Institusi \"$institusi\"")
                setPositiveButton("Ok") { _, _ ->
                    findNavController().popBackStack()
                }
                setCancelable(false)
            }.create().show()
        }
    }
}
