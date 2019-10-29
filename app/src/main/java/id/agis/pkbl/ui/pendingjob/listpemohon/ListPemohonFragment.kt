package id.agis.pkbl.ui.pendingjob.listpemohon


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.data.local.entities.PemohonEntity
import kotlinx.android.synthetic.main.fragment_list_pemohon.*

/**
 * A simple [Fragment] subclass.
 */
class ListPemohonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_pemohon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pemohon = PemohonEntity(0, "Andi Sofyan", "100.000.000")
        val listPemohon = mutableListOf<PemohonEntity>()
        for (x in 0 until 10) {
            listPemohon.add(pemohon)
        }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListPemohonAdapter(listPemohon)
        }
    }


}
