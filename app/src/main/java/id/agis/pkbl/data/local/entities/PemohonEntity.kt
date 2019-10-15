package id.agis.pkbl.data.local.entities

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
open class PemohonEntity (
    @PrimaryKey
    var idPemohon: Int,

    var namaLengkap: String,

    var nilaiPengajuan: String
): RealmObject(), Parcelable{
    constructor(): this(0, "", "")
}