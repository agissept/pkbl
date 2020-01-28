package id.agis.pkbl.util


import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import id.agis.pkbl.model.FileModel
import id.agis.pkbl.ui.detail.LoadingInterface
import okhttp3.ResponseBody
import java.io.*


object FileUtil {
    fun Uri.getPath(context: Context): String {
        val uri = this
        var filePath = ""

        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            } else {

                if (Build.VERSION.SDK_INT > 20) {
                    //getExternalMediaDirs() added in API 21
                    val external = context.externalMediaDirs
                    if (external.size > 1) {
                        filePath = external[1].absolutePath
                        filePath = filePath.substring(0, filePath.indexOf("Android")) + split[1]
                    }
                } else {
                    filePath = "/storage/" + type + "/" + split[1]
                }
                return filePath
            }

        } else if (isDownloadsDocument(uri)) {
            // DownloadsProvider
//            val id = DocumentsContract.getDocumentId(uri)
            //final Uri contentUri = ContentUris.withAppendedId(
            // Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)

            try {
                cursor = context.contentResolver.query(uri, projection, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    val result = cursor.getString(index)
                    cursor.close()
                    return result
                }
            } finally {
                cursor?.close()
            }
        } else if (DocumentsContract.isDocumentUri(context, uri)) {
            // MediaProvider
            val wholeID = DocumentsContract.getDocumentId(uri)

            // Split at colon, use second item in the array
            val ids = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val id: String
            val type: String
            if (ids.size > 1) {
                id = ids[1]
                type = ids[0]
            } else {
                id = ids[0]
                type = ids[0]
            }

            var contentUri: Uri? = null
            when (type) {
                "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

            val selection = "_id=?"
            val selectionArgs = arrayOf(id)
            val column = "_data"
            val projection = arrayOf(column)
            val cursor = context.contentResolver.query(
                contentUri!!,
                projection, selection, selectionArgs, null
            )

            if (cursor != null) {
                val columnIndex = cursor.getColumnIndex(column)

                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex)
                }
                cursor.close()
            }
            return filePath
        } else {
            val projection = arrayOf(MediaStore.Audio.Media.DATA)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            if (cursor != null) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                if (cursor.moveToFirst())
                    filePath = cursor.getString(columnIndex)
                cursor.close()
            }


            return filePath
        }
        return ""
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }


    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

//    fun isMediaDocument(uri: Uri): Boolean {
//        return "com.android.providers.media.documents" == uri.authority
//    }
//
//    fun isGooglePhotosUri(uri: Uri): Boolean {
//        return "com.google.android.apps.photos.content" == uri.authority
//    }


    fun createNewFolder(name: String, type: String): File {
        val folder = File(Environment.getExternalStorageDirectory().toString(), "PKBL/$type/$name")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        return folder
    }

    private fun createTempFile(folder: File, fileName: String): File {
        val tempFile = File(folder, fileName)
        tempFile.createNewFile()

        return tempFile
    }

    fun copyFile(path: String, idPengajuan: String) {
        val file = File(path)
        val folder = createNewFolder(idPengajuan, "Upload")
        val tempFile = createTempFile(folder, file.name)

        file.inputStream().use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }

    fun deleteFile(path: String) {
        val file = File(path)
        if (file.isDirectory) {
            file.deleteRecursively()
        } else {
            file.delete()
        }
    }

    fun Intent?.getUri(): List<Uri> {
        val listUri = mutableListOf<Uri>()
        if (this?.clipData != null) {
            this.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    clipData.getItemAt(i)?.uri?.let {
                        listUri.add(it)
                    }
                }
            }
        } else {
            this?.data?.let {
                listUri.add(it)
            }
        }

        return listUri
    }

    fun Context.launchFileIntent(fileModel: FileModel) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = FileProvider.getUriForFile(this, packageName, File(fileModel.path))
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION.or(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivity(Intent.createChooser(intent, "Select Application"))
    }

     fun writeResponseBodyToDisk(
        body: ResponseBody,
        name: String,
        idPengajuan: String,
     loadingInterface: LoadingInterface
    ): Boolean {
        try {
            val futureStudioIconFile =
                File("storage/emulated/0/pkbl/Download/$idPengajuan/$name")

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()
                    loadingInterface.dowloandProgress(fileSize.toInt())
                    Log.d("AAAA", "file download: $fileSizeDownloaded of $fileSize")
                }

                outputStream.flush()

                return true
            } catch (e: IOException) {
                return false
            } finally {
                inputStream?.close()

                outputStream?.close()
            }
        } catch (e: IOException) {
            return false
        }

    }
}
