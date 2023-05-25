package com.carefer.core.utils

import android.app.Activity
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.io.*


fun Bundle.putEnum(key: String, enum: Enum<*>) {
    this.putString(key, enum.name)
}

inline fun <reified T : Enum<*>> Bundle.getEnum(key: String): T {
    return enumValueOf(getString(key) ?: "")
}

fun String.decodeBase64ToBitmap(): Bitmap? {
    var modifiedString = this
    if (this.contains("data:image/png;base64,")) {
        modifiedString = removePrefix("data:image/png;base64,")
    }
    val imageBytes = Base64.decode(modifiedString, Base64.DEFAULT)
    return try {
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    } catch (e: Exception) {
        null
    }
}

fun String.extractUserNameInitials(charsNumbers: Int): String {
    return try {
        // if display name contains any special chars, get only the first char
        when {
            this.contains(' ') -> {
                this
                    .split(' ')
                    .mapNotNull { it.firstOrNull()?.toString() }
                    .reduce { acc, s -> acc + s }
                    .slice(0..1)
                    // Add spaces between chars to avoid concatination in Arabic names
                    .replace("", " ").trim()
            }

            this.contains(Regex("[^\\w\\u0621-\\u064A\\s]")) -> {
                take(1)
            }

            else -> {
                this.take(charsNumbers).replace("", " ").trim()
            }
        }
    } catch (e: Exception) {
        this.take(1)
    }
}

fun View.showPopup(menuRes: Int, onItemClickAction: (Int) -> Boolean) {
    val popup = PopupMenu(this.context, this)
    popup.setOnMenuItemClickListener {
        onItemClickAction(it.itemId)
    }
    val inflater = popup.menuInflater
    inflater.inflate(menuRes, popup.menu)
    popup.show()
}

fun Fragment.showDialog(dialog: DialogFragment) {
    dialog.show(childFragmentManager, dialog::class.java.name)
}

fun Fragment.getIntent(): Intent? {
    return requireActivity().intent
}

inline fun <reified T> Any.mapTo(defaultValue: T): T =
    try {
        GsonBuilder().create().run {
            fromJson(toJson(this@mapTo), T::class.java)
        }
    } catch (e: Exception) {
        defaultValue
    }

inline fun <reified T> List<T>.copyList(): ArrayList<T> {
    val gsonList = Gson().toJson(this)
    return Gson().fromJson<ArrayList<T>>(gsonList)
}

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

fun String.getMcitDriveFilePath(): String {
    return this.substringAfterLast("/")
}

fun String.getMcitDriveFolderPath(): String {
    return this.substringBeforeLast("/").substringAfterLast("/")
}

fun String.getMcitDriveFolderNavPath(folderFullPath: String): String {
    return if (this == "/") {
        this.plus(folderFullPath.getMcitDriveFolderPath())
    } else {
        this.plus("/${folderFullPath.getMcitDriveFolderPath()}")
    }
}

fun String.getMcitDriveFolderBackNavPath(): String {
    return this.substringBeforeLast("/")
}

fun Context.copyToClipboard(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}


fun Activity.createImageFileInCacheStorage(): Uri {
    val pathname = "${this.cacheDir}"
    val folder = File(pathname)
    folder.mkdirs()
    val file = File(folder, "${System.currentTimeMillis()}.jpg")
    file.createNewFile()
    return file.toUri()
}

fun Activity.createFileInCacheStorage(): String {
    val pathname = "${this.cacheDir}"
    val folder = File(pathname)
    folder.mkdirs()
    val file = File(folder, "${System.currentTimeMillis()}")
    file.createNewFile()
    return file.absolutePath
}

fun Activity.getTempFileUri(): Uri {
    val tempFile =
        File.createTempFile(System.currentTimeMillis().toString(), ".jpg", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

    return FileProvider.getUriForFile(
        applicationContext,
        "${this.packageName}.provider",
        tempFile
    )
}

fun String.getFileExt(): String {
    val ext = this.substringAfterLast(".").lowercase()
    return ".$ext"
}

fun String.createPartFromString(): RequestBody {
    return this.toRequestBody(MultipartBody.FORM)
}

fun Uri.getFilePartFromUri(): MultipartBody.Part? {
    val file = this.path?.let { File(it) }
    return file?.let {
        MultipartBody.Part.create(
            file.asRequestBody("*/*".toMediaType())
        )
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun Activity.writeResponseBodyToDisk(body: ResponseBody, fileName: String): Boolean {
    return try {
        val pathname = "${this.getExternalFilesDir(null)}"
        val folder = File(pathname)
        folder.mkdirs()
        val file = File(folder, fileName)
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            val fileReader = ByteArray(4096)
            val fileSize = body.contentLength()
            var fileSizeDownloaded: Long = 0
            inputStream = body.byteStream()
            outputStream = FileOutputStream(file)
            while (true) {
                val read = inputStream.read(fileReader)
                if (read == -1) {
                    break
                }
                outputStream.write(fileReader, 0, read)
                fileSizeDownloaded += read.toLong()
                Log.d("TAG", "file download: $fileSizeDownloaded of $fileSize")
            }
            outputStream.flush()
            true
        } catch (e: IOException) {
            false
        } finally {
            saveFileUsingMediaStore(this, fileName)
            inputStream?.close()
            outputStream?.close()
        }
    } catch (e: IOException) {
        false
    }
}

fun File.getFileMimeType(): String {
    val mimeMap: MimeTypeMap = MimeTypeMap.getSingleton()
    return mimeMap.getMimeTypeFromExtension(this.extension) ?: ""
}

@RequiresApi(Build.VERSION_CODES.Q)
private fun Activity.saveFileUsingMediaStore(
    context: Context,
    fileName: String
) {
    val file = File(this.getExternalFilesDir(null), fileName)

    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, file.getFileMimeType())
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }
    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
    val inputStream = context.contentResolver.openInputStream(file.toUri())
    if (uri != null) {
        inputStream.use { input ->
            resolver.openOutputStream(uri).use { output ->
                input!!.copyTo(output!!, DEFAULT_BUFFER_SIZE)
            }
        }
    }
}