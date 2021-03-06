package id.agis.pkbl.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import id.agis.pkbl.util.toTitleCase
import kotlinx.android.synthetic.main.item_job.view.*

class HomeAdapter(val listPengajuan: List<Pengajuan>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = if (listPengajuan.size < 10) {
        listPengajuan.size
    } else {
        10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pengajuan = listPengajuan[position]
        holder.apply {
            val indexOfSpace = pengajuan.nama.indexOf(' ')
            if (indexOfSpace > 0) {
                tvNama.text = pengajuan.nama.take(indexOfSpace).toTitleCase()
            } else {
                tvNama.text = pengajuan.nama.toTitleCase()
            }
            tvAlamat.text = pengajuan.kota.toTitleCase()

            itemView.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionNavHomeToNavDetailPengajuan(pengajuan, "home")
                itemView.findNavController().navigate(action)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.tv_nama
        val tvAlamat: TextView = itemView.tv_alamat
    }
}