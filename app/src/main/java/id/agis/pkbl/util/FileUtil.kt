package id.agis.pkbl.util


import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import java.io.File


object FileUtil {
    fun getPath(context: Context, uri: Uri): String? {
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
        return null
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


    fun getFileName(uri: Uri, context: Context): String? {
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

    fun getMimeType(uri: Uri, context: Context): String? {
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val mime = MimeTypeMap.getSingleton()
            mime?.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
    }

    fun createFolder(
        user: String,
        typePemohon: String,
        typeFolder: String,
        fileName: String
    ): File {
        val folder = File(
            Environment.getExternalStorageDirectory().toString(),
            "PKBL/$user/$typePemohon/$typeFolder/$fileName"
        )
        folder.createNewFile()
        return folder
    }

    fun copyFile(src: File, dst: File) {
        src.inputStream().use { input ->
            dst.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }
}
