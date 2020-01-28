package id.agis.pkbl.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import kotlinx.android.synthetic.main.item_status.view.*
import java.text.SimpleDateFormat
import java.util.*

class StatusPengajuanAdapter(val pengajuan: Pengajuan) :
    RecyclerView.Adapter<StatusPengajuanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            if (viewType == 0) {
                LayoutInflater.from(parent.context).inflate(R.layout.item_status, parent, false)
            } else {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_status_bottom, parent, false)
            }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return when (pengajuan.status) {
            "Penugasan" -> 1
            "Penilaian" -> 2
            "Persetujuan" -> 3
            "Pencairan" -> 4
            "Ditolak" -> 4
            else -> 5
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            1
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
        val dateFormat = SimpleDateFormat("EEE, MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val dateTime: Date

        val status: String
        when (position) {
            0 -> {
                status = "Dikirim kepada surveyor (${pengajuan.surveyor})"
                 dateTime = dateTimeFormat.parse(pengajuan.time)
            }
            1 -> {
                status = "Telah disurvey oleh ${pengajuan.surveyor}"
                dateTime = dateTimeFormat.parse(pengajuan.tanggalSurvey)
            }
            2 -> {
                status = "Telah dinilai oleh ${pengajuan.penilai}"
                dateTime = dateTimeFormat.parse(pengajuan.tanggalPenilaian)
            }
            3 -> {
                dateTime = dateTimeFormat.parse(pengajuan.tanggalPersetujuan)
                status = if (pengajuan.status == "Ditolak") {
                    "Pengajuan ditolak oleh ${pengajuan.penyetuju}"
                } else {
                    "Pengajuan diterima oleh ${pengajuan.penyetuju}"
                }
            }
            else -> {
                dateTime = dateTimeFormat.parse(pengajuan.tanggalPencairan)
                status = "Dana telah dicairkan oleh ${pengajuan.bendahara}"
            }
        }
        holder.tvStatus.text = status

        val date = dateFormat.format(dateTime)
        val time = timeFormat.format(dateTime)
        holder.tvTime.text = time
        holder.tvDate.text = date
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.tv_time
        val tvDate: TextView = itemView.tv_date
        val tvStatus: TextView = itemView.tv_status
    }
}


