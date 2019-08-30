package id.agis.pkbl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kemitraan (
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
): Parcelable