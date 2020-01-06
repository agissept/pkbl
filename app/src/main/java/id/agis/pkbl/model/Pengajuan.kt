package id.agis.pkbl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pengajuan(
    val id: Int,
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
    val penilaian: String
): Parcelable