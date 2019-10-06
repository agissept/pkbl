package id.agis.pkbl.ui.binalingkungan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.agis.pkbl.R
import id.agis.pkbl.model.BinaLingkugan
import id.agis.pkbl.ui.detail.binalingkungan.DetailBinaLingkunganActivity
import kotlinx.android.synthetic.main.item_list.view.*
import org.jetbrains.anko.startActivity

class BinaLingkunganAdapter(private val listBinaLingkungan : List<BinaLingkugan>):
    RecyclerView.Adapter<BinaLingkunganAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBinaLingkungan.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listBinaLingkungan[position]

        holder.tvTitle.text = data.namaLengkap
        holder.tvBody.text = data.nilaiPengajuan
        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity<DetailBinaLingkunganActivity>()
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.tv_title
        val tvBody: TextView = itemView.tv_body
    }
}