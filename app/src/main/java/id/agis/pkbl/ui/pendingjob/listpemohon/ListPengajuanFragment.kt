package id.agis.pkbl.ui.pendingjob.listpemohon


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import kotlinx.android.synthetic.main.fragment_list_pemohon.*

/**
 * A simple [Fragment] subclass.
 */
class ListPengajuanFragment : Fragment() {
    private val listPemohon = mutableListOf<Pengajuan>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_pemohon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = arguments?.let { ListPengajuanFragmentArgs.fromBundle(it) }
        listPemohon.apply {
            clear()
            addAll(argument!!.pengajuan)
        }
        val source = argument!!.source
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListPengajuanAdapter(listPemohon, source)
        }
    }

}
