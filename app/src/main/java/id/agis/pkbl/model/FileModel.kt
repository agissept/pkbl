package id.agis.pkbl.model

import id.agis.pkbl.util.FileType

data class FileModel(
    val path: String,
    val fileType: FileType,
    val name: String,
    val sizeInMB: Double,
    val extension: String = "",
    val subFiles: Int = 0
)