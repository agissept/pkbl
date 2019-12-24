package id.agis.pkbl.ui.dashboard.persektor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import kotlinx.android.synthetic.main.item_dashboard.view.*

class PerSektorAdapter(val listPengajuan: List<Pengajuan>): RecyclerView.Adapter<PerSektorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPengajuan.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pengajuan = listPengajuan[position]
        val jumlahDana = listPengajuan.sumBy {
            it.dana.toInt()
        }
        val percent = pengajuan.dana.toFloat() / jumlahDana *  100

        holder.apply {
            sektor.text = pengajuan.sektor
            dana.text = pengajuan.dana
            persentase.text = percent.toString()
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sektor: TextView = itemView.sektor
        val dana: TextView = itemView.dana
        val persentase: TextView = itemView.persentase
    }
}