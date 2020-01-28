package id.agis.pkbl.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pengajuan(
    val id: String,
    val nama: String,
    val nik: String,
    val dana: String,
    val sektor: String,
    val provinsi: String,
    val kota: String,
    val kecamatan: String,
    val kelurahan: String,
    val jalan: String,
    val koordinat: String,
    val surveyor: String,
    val type: String,
    val status: String,
    val time: String,
    val tujuan: String,
    val alasan: String,
    val instansi: String,
    val penilaian: String,
    val penilai: String,
    val penyetuju: String,
    val bendahara: String,

    @SerializedName("tanggal_survey")
    val tanggalSurvey: String,

    @SerializedName("tanggal_penilaian")
    val tanggalPenilaian: String,

    @SerializedName("tanggal_penyetujuan")
    val tanggalPersetujuan: String,

    @SerializedName("tanggal_pencairan")
    val tanggalPencairan: String
) : Parcelable