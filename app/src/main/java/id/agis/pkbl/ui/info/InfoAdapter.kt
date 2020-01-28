package id.agis.pkbl.ui.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.agis.pkbl.R
import id.agis.pkbl.model.Info
import kotlinx.android.synthetic.main.item_info.view.*

class InfoAdapter (private val listInfo: List<Info>): RecyclerView.Adapter<InfoAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_info, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listInfo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = listInfo[position]
        holder.itemView.apply {
            tv_title.text = info.title
            tv_description.text = info.description

            setOnClickListener {
                val action = InfoFragmentDirections.actionNavInfoToNavDetaiInfo(info)
                findNavController().navigate(action)
            }
        }
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}