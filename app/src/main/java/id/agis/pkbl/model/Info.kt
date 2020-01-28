package id.agis.pkbl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Info(
    val id: Int,
    val title: String,
    val description: String,
    val time: String
): Parcelable