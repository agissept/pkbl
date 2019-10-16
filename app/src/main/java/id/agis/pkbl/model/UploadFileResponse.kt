package id.agis.pkbl.model

data class UploadFileResponse(
    val status: Status,
    val result: Result
)

data class Result(
    val idPemohon: String,
    val file: Array<File>
)

data class File(
    val fieldname: String,
    val originalname: String,
    val encoding: String,
    val mimetype: String,
    val destination: String,
    val filename: String,
    val path: String,
    val size: Int
)