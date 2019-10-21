package id.agis.pkbl.data.local.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FileEntity(
    @PrimaryKey
    var name: String,
    var type: String,
    var idPemohon: Int,
    var idUser: Int,
    var url: String?,
    var uri: String?,
    var path: String?,
    var uploaded: Boolean,
    var downloaded: Boolean
) : RealmObject() {
    constructor() : this("", "", 0, 0, "", "", "", false, false)
}