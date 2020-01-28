package id.agis.pkbl.ui.documentfolder

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.agis.pkbl.R
import id.agis.pkbl.model.FileModel
import id.agis.pkbl.util.FileType
import kotlinx.android.synthetic.main.item_recycler_file.view.*
import java.io.File

class FilesRecyclerAdapter(
    private val listFile: List<FileModel>
) : RecyclerView.Adapter<FilesRecyclerAdapter.ViewHolder>() {

    lateinit var onItemClickListener: ((FileModel) -> Unit)
    lateinit var onItemLongClickListener: ((FileModel) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_file, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listFile.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileModel = listFile[position]
        holder.itemView.apply {
            tv_name.text = fileModel.name

            if (fileModel.fileType == FileType.FOLDER) {
                tv_folder_size.visibility = View.VISIBLE
                tv_file_size.visibility = View.GONE
                tv_folder_size.text = "(${fileModel.subFiles} files)"
                if(fileModel.subFiles > 0){
                    Glide.with(context).load(R.drawable.ic_folder).into(iv_icon)
                }else{
                    Glide.with(context).load(R.drawable.ic_folder_empty).into(iv_icon)
                }
            } else {
                tv_folder_size.visibility = View.GONE
                tv_file_size.visibility = View.VISIBLE
                tv_file_size.text = "${String.format("%.2f", fileModel.sizeInMB)} mb"

                if (fileModel.extension == "pdf") {
                    Glide.with(context).load(R.drawable.type_pdf).into(iv_icon)
                } else if (fileModel.extension == "jpg" || fileModel.extension == "png") {
                    val image = File(fileModel.path)
                    val uri = Uri.fromFile(image)
                    Glide.with(context).load(uri).into(iv_icon)
                    iv_icon.scaleType = ImageView.ScaleType.CENTER_CROP
                } else {
                    Glide.with(context).load(R.drawable.type_unknow).into(iv_icon)
                }
            }

            setOnClickListener {
                onItemClickListener(fileModel)
            }
            setOnLongClickListener {
                onItemLongClickListener(fileModel)
                true
            }
        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}