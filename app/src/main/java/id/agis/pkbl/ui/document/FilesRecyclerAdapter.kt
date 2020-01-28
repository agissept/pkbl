package id.agis.pkbl.ui.document

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.agis.pkbl.R
import id.agis.pkbl.model.FileModel
import id.agis.pkbl.util.FileType
import kotlinx.android.synthetic.main.item_recycler_file.view.*

class FilesRecyclerAdapter : RecyclerView.Adapter<FilesRecyclerAdapter.ViewHolder>() {

    var onItemClickListener: ((FileModel) -> Unit)? = null
    var onItemLongClickListener: ((FileModel) -> Unit)? = null

    var filesList = listOf<FileModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_file, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = filesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindView(position)

    fun updateData(filesList: List<FileModel>) {
        this.filesList = filesList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickListener?.invoke(filesList[adapterPosition])
        }

        override fun onLongClick(v: View?): Boolean {
            onItemLongClickListener?.invoke(filesList[adapterPosition])
            return true
        }

        fun bindView(position: Int) {
            val fileModel = filesList[position]
            itemView.tv_name.text = fileModel.name

            if (fileModel.fileType == FileType.FOLDER) {
                itemView.tv_folder_size.visibility = View.VISIBLE
                itemView.tv_file_size.visibility = View.GONE
                itemView.tv_folder_size.text = "(${fileModel.subFiles} files)"
            } else {
                itemView.tv_folder_size.visibility = View.GONE
                itemView.tv_file_size.visibility = View.VISIBLE
                itemView.tv_file_size.text = "${String.format("%.2f", fileModel.sizeInMB)} mb"
            }
        }
    }
}