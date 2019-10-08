package id.agis.pkbl.ui.detail.binalingkungan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.agis.pkbl.R
import id.agis.pkbl.model.UserFile
import kotlinx.android.synthetic.main.image_list.view.*

class DetailBinaLingkunganAdapter(
    val context: Context,
    private val listFile: List<UserFile>
) : RecyclerView.Adapter<DetailBinaLingkunganAdapter.ViewHolder>() {
    lateinit var onItemClick: ((UserFile) -> Unit)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFile.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = listFile[position].name
        holder.itemView.setOnClickListener {
            onItemClick(listFile[position])
        }

//        if (listFile.isNotEmpty() && position <= listFile.size-1 && listFile[position] != null) {
//            Glide.with(context).load(listFile[position]).into(holder.image)
//        } else {
//            Glide .with(context).load("https://via.placeholder.com/100").into(holder.image)
//        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.image
        val title: TextView = itemView.tv_title
    }
}