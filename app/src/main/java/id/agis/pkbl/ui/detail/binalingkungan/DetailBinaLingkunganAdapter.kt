package id.agis.pkbl.ui.detail.binalingkungan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.agis.pkbl.R
import kotlinx.android.synthetic.main.image_list.view.*

class DetailBinaLingkunganAdapter(
    val context: Context,
    private val listImage: List<String?>
) : RecyclerView.Adapter<DetailBinaLingkunganAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listImage.isNotEmpty() && position <= listImage.size-1 && listImage[position] != null) {
            Glide.with(context).load(listImage[position]).into(holder.image)
        } else {
            Glide.with(context).load("https://via.placeholder.com/100").into(holder.image)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.image
    }
}