package id.agis.pkbl.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BinaLingkugan (
    @SerializedName("id_pemohon")
    val idPemohon: Int,

    @SerializedName("nama_lengkap")
    val namaLengkap: String,

    @SerializedName("nilai_pengajuan")
    val nilaiPengajuan: String
): Parcelable