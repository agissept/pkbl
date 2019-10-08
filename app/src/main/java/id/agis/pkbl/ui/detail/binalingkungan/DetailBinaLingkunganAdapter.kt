package id.agis.pkbl.ui.detail.binalingkungan

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.agis.pkbl.R
import id.agis.pkbl.model.UserFile
import kotlinx.android.synthetic.main.file_list.view.*
import org.jetbrains.anko.toast
import java.io.File

class DetailBinaLingkunganAdapter(
    val context: Context,
    private val listFile: List<UserFile>
) : RecyclerView.Adapter<DetailBinaLingkunganAdapter.ViewHolder>() {
    lateinit var onItemClick: ((UserFile) -> Unit)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.file_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFile.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = listFile[position]
        holder.title.text = file.name
        holder.itemView.setOnClickListener {
            onItemClick(file)
            it.context.toast(file.uri.path!!)
        }

        if (file.type == "pdf") {
            Glide.with(context).load(R.drawable.type_pdf).into(holder.icon)
        } else if (file.type == "jpg" || file.type == "ong"){
            val image = File(file.path)
            val uri = Uri.fromFile(image)
            Glide.with(context).load(uri).into(holder.image)
            holder.image.visibility = View.VISIBLE
        } else {
            Glide.with(context).load(R.drawable.type_unknow).into(holder.icon)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.iv_image
        val title: TextView = itemView.tv_title
        val icon: ImageView = itemView.iv_icon
    }
}