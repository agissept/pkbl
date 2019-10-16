package id.agis.pkbl.ui.detail.binalingkungan

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.PKBLRepository
import id.agis.pkbl.model.UserFile
import id.agis.pkbl.util.FileUtil.copyFile
import id.agis.pkbl.util.FileUtil.createFolder
import id.agis.pkbl.util.FileUtil.getFileName
import id.agis.pkbl.util.FileUtil.getMimeType
import id.agis.pkbl.util.FileUtil.getPath
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DetailBinaLingkunganViewModel(private val pkblRepository: PKBLRepository) : ViewModel() {
    val listFileLiveData = MutableLiveData<List<UserFile>>()
    private val listFile = mutableListOf<UserFile>()

    fun uploadFile(listFile: List<UserFile>, username: String, idPemohon: Int) {
        listFile.forEach {
            val file = File(it.path)
            val target = createFolder(username, "Bina Lingkungan", "Upload", file.name)

            copyFile(file, target)

            val requestBody = RequestBody.create(MediaType.parse("multipart"), target)
            val multipartBody = MultipartBody.Part.createFormData("files", target.name, requestBody)

            pkblRepository.uploadFile(multipartBody, idPemohon)
        }
    }

    fun insertUri(data: Intent?, context: Context) {
        if (data?.clipData != null) {
            data.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    clipData.getItemAt(i)?.uri?.let {
                        listFile.add(
                            UserFile(
                                (it),
                                getPath(context, it),
                                getFileName(it, context),
                                getMimeType(it, context)
                            )
                        )
                    }
                }
            }
        } else {
            data?.data?.let {
                listFile.add(
                    UserFile(
                        (it),
                        getPath(context, it),
                        getFileName(it, context),
                        getMimeType(it, context)
                    )
                )
            }
        }
        listFile.reverse()
        listFileLiveData.postValue(listFile)
    }
}