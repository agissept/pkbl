package id.agis.pkbl.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import kotlinx.android.synthetic.main.item_job.view.tv_alamat
import kotlinx.android.synthetic.main.item_job.view.tv_nama

class SearchAdapter(val listPengajuan: List<Pengajuan>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when (viewType) {
            0 -> LayoutInflater.from(parent.context).inflate(R.layout.item_pemohon_both, parent, false)
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.item_pemohon_top, parent, false)
            2 -> LayoutInflater.from(parent.context).inflate(R.layout.item_pemohon_bottom, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(
                R.layout.item_pemohon,
                parent,
                false
            )
        }
        println(viewType.toString() + "viewtype aaaaaaaaaaaaaaaaaaaaaaa")
        println()

        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            listPengajuan.size == 1 -> 0
            position == 0 -> 1
            position == listPengajuan.size - 1 -> 2
            else -> 3
        }
    }

    override fun getItemCount(): Int = listPengajuan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.itemView.setBackgroundResource(R.drawable.bg_item_pengajuan_top)
        }
        val pengajuan = listPengajuan[position]

        holder.tvNama.text = pengajuan.nama
        val alamat = "${pengajuan.jalan}, ${pengajuan.kota}"
        holder.tvAlamat.text = alamat
        holder.itemView.setOnClickListener {
            val action = SearchFragmentDirections.actionNavSearchToNavDetailPengajuan(
                pengajuan, ""
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.tv_nama
        val tvAlamat: TextView = itemView.tv_alamat
    }
}