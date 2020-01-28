package id.agis.pkbl.ui.detailinfo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.agis.pkbl.R
import kotlinx.android.synthetic.main.item_info.*
import kotlinx.android.synthetic.main.item_status.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DetailInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val info = arguments?.let {
            DetailInfoFragmentArgs.fromBundle(it).info
        }

        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
        val dateTime = dateTimeFormat.parse(info?.time)

        val dateFormat = SimpleDateFormat("EEE, MMM yyyy, hh:mm", Locale.getDefault())
        val date = dateFormat.format(dateTime)

        tv_time.text = date
        tv_title.text = info?.title
        tv_description.text = info?.description

    }


}
