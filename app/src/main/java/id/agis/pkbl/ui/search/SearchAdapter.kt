package id.agis.pkbl.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.agis.pkbl.R
import id.agis.pkbl.main.MainActivity
import id.agis.pkbl.model.Pengajuan
import id.agis.pkbl.ui.pendingjob.listpemohon.ListPengajuanFragmentDirections
import kotlinx.android.synthetic.main.item_job.view.*
import kotlinx.android.synthetic.main.item_job.view.tv_alamat
import kotlinx.android.synthetic.main.item_job.view.tv_nama
import kotlinx.android.synthetic.main.item_pemohon.view.*

class SearchAdapter(val listPengajuan: List<Pengajuan>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pemohon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listPengajuan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pengajuan = listPengajuan[position]

        holder.tvNama.text = pengajuan.nama
        val alamat = "${pengajuan.jalan}, ${pengajuan.kota}"
        holder.tvAlamat.text = alamat
        holder.itemView.setOnClickListener {
            val action = ListPengajuanFragmentDirections.actionNavListPemohonToNavDetailPengajuan(
                pengajuan, ""
            )
            MainActivity().findNavController(R.id.nav_host_fragment).navigate(action)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.tv_nama
        val tvAlamat: TextView = itemView.tv_alamat
    }
}