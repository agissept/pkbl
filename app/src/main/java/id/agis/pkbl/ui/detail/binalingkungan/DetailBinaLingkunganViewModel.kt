package id.agis.pkbl.ui.detail.binalingkungan

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.PKBLRepository
import id.agis.pkbl.data.local.entities.FileEntity
import id.agis.pkbl.util.FileUtil.copyFile
import id.agis.pkbl.util.FileUtil.createFolder
import io.realm.RealmResults
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DetailBinaLingkunganViewModel(private val pkblRepository: PKBLRepository) : ViewModel() {
    var idPemohon = 0
    val listFile: LiveData<RealmResults<FileEntity>> get() = pkblRepository.getAllFile(idPemohon)

    fun uploadFile(listFile: List<FileEntity>, idPemohon: Int) {
        listFile.forEach {
            val file = File(it.path)
            val requestBody = RequestBody.create(MediaType.parse("multipart"), file)
            val multipartBody = MultipartBody.Part.createFormData("files", file.name, requestBody)

            pkblRepository.uploadFile(multipartBody, idPemohon)
        }
    }

    fun getUri(data: Intent?): List<Uri> {
        val listUri = mutableListOf<Uri>()
        if (data?.clipData != null) {
            data.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    clipData.getItemAt(i)?.uri?.let {
                        listUri.add(it)
                    }
                }
            }
        } else {
            data?.data?.let {
                listUri.add(it)
//                listFile.add(
//                    UserFile(
//                        (it),
//                        getPath(context, it),
//                        getFileName(it, context),
//                        getMimeType(it, context)
//                    )
//                )
            }
        }

        return listUri
    }

    fun deleteFile(position: Int) {
//        listFile.removeAt(position - 1)
//        listFileLiveData.postValue(listFile)
    }

     fun copyFile(path: String, username: String): File {
        val file = File(path)
        val target = createFolder(
            username,
            "Bina Lingkungan",
            "Upload",
            System.currentTimeMillis().toString() + file.name
        )

        copyFile(file, target)

        return file
    }

    fun insertFile(fileEntity: FileEntity){
        pkblRepository.insertFile(fileEntity)
    }
}