package id.agis.pkbl.ui.pendingjob.selesai.listselesai


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.agis.pkbl.R

/**
 * A simple [Fragment] subclass.
 */
class ListSelesaiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_selesai, container, false)
    }


}
