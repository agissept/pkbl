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
//import id.agis.pkbl.data.local.entities.FileEntity
import kotlinx.android.synthetic.main.file_list.view.*
import java.io.File

class DetailBinaLingkunganAdapter(
    val context: Context,
    private val listFile: MutableList<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    lateinit var onItemClickFile: ((FileEntity) -> Unit)
    lateinit var onItemClick: (() -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            ViewHolder0(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.add_file_list,
                    parent,
                    false
                )
            )
        } else {
            ViewHolder1(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.file_list,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return if (listFile.isNotEmpty()) {
            listFile.size + 1
        } else {
            1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == 0) {
            val viewHolder = ViewHolder0(holder.itemView)
            viewHolder.itemView.setOnClickListener {
                onItemClick()
            }
        } else {
            val viewHolder = ViewHolder1(holder.itemView)
            val file = listFile[position - 1]
//            viewHolder.title.text = file.name
            viewHolder.itemView.setOnClickListener {
//                onItemClickFile(file)
            }
            viewHolder.itemView.setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(position, 123, 0, "Delete")
            }

//            if (file.type == "pdf") {
//                Glide.with(context).load(R.drawable.type_pdf).into(viewHolder.icon)
//            } else if (file.type == "jpg" || file.type == "png") {
//                val image = File(file.path)
//                val uri = Uri.fromFile(image)
//                Glide.with(context).load(uri).into(viewHolder.image)
//                viewHolder.image.visibility = View.VISIBLE
//            } else {
//                Glide.with(context).load(R.drawable.type_unknow).into(viewHolder.icon)
//            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            0
        } else {
            1
        }
    }

    inner class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.iv_image
        val title: TextView = itemView.tv_nama
        val icon: ImageView = itemView.iv_icon
    }
}