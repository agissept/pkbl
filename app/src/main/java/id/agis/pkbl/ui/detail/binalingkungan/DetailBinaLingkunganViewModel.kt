package id.agis.pkbl.ui.detail.binalingkungan

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.model.UserFile
import okhttp3.MultipartBody
import java.io.File

class DetailBinaLingkunganViewModel : ViewModel() {
    val listFileLiveData = MutableLiveData<List<UserFile>>()
    private val listFile = mutableListOf<UserFile>()

    fun uploadImage(imageName: MultipartBody.Part) {
//   RemoteRepository().uploadImage(imageName)
    }

    fun insertUri(data: Intent?, context: Context) {
        if (data != null) {
            data.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    clipData.getItemAt(i)?.uri?.let {
                        listFile.add(
                            UserFile((it), getFileName(it, context), getMimeType(it, context))
                        )
                    }
                }
            }
        } else {
            data?.data?.let {
                listFile.add(UserFile((it), getFileName(it, context), getMimeType(it, context)))
            }
        }
        listFileLiveData.postValue(listFile)
    }

    private fun getFileName(uri: Uri, context: Context): String? {
        val uriString = uri.toString()
        val myFile = File(uriString)
        var displayName: String? = null
        if (uriString.startsWith("content://")) {
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver.query(uri, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    displayName =
                        cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.name
        }

        return displayName
    }

    private fun getMimeType(uri: Uri, context: Context): String? {
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val mime = MimeTypeMap.getSingleton()
            mime?.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
    }
}