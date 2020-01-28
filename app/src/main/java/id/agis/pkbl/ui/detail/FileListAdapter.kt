package id.agis.pkbl.ui.detail

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
import id.agis.pkbl.model.FileModel
//import id.agis.pkbl.data.local.entities.FileEntity
import kotlinx.android.synthetic.main.item_file.view.*
import java.io.File

class FileListAdapter(
    private val context: Context,
    private val listFile: List<FileModel>,
    private val isAddFileActive: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var onItemClickListener: ((FileModel) -> Unit)
    lateinit var onItemLongClickListener: ((FileModel) -> Unit)
    lateinit var onItemAddClickListener: (() -> Unit)
    lateinit var onDownloadClickListener: ((FileModel) -> Unit)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_add_file, parent, false)
            ViewHolder0(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
            ViewHolder1(view)
        }
    }

    override fun getItemCount(): Int =
        if (listFile.isNotEmpty() && isAddFileActive) listFile.size + 1 else if (isAddFileActive) 1 else listFile.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            val viewHolder = ViewHolder0(holder.itemView)
            viewHolder.itemView.setOnClickListener {
                onItemAddClickListener()
            }
        } else {
            val fileModel = if (isAddFileActive) listFile[position - 1] else listFile[position]

            val viewHolder = ViewHolder1(holder.itemView)
            viewHolder.title.text = fileModel.name
            viewHolder.itemView.setOnClickListener {
                onItemClickListener(fileModel)
            }
            viewHolder.itemView.setOnLongClickListener {
                onItemLongClickListener(fileModel)
                true
            }

            if (fileModel.extension == "pdf") {
                Glide.with(context).load(R.drawable.type_pdf).into(viewHolder.icon)
            } else if (fileModel.extension == "jpg" || fileModel.extension == "png") {
                val image = File(fileModel.path)
                val uri = Uri.fromFile(image)
                Glide.with(context).load(uri).into(viewHolder.image)
                viewHolder.image.visibility = View.VISIBLE
            } else {
                Glide.with(context).load(R.drawable.type_unknow).into(viewHolder.icon)
            }

            if(fileModel.path == ""){
                viewHolder.download.visibility = View.VISIBLE
            }else{
                viewHolder.download.visibility = View.GONE
            }

        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0 && isAddFileActive) 0 else 1

    inner class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.iv_image
        val title: TextView = itemView.tv_nama
        val icon: ImageView = itemView.iv_icon
        val download: TextView = itemView.tv_download
    }
}