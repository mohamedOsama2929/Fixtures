package com.carefer.core.domain.entities

import java.io.File

data class FileUploadQuery(
    var file: ArrayList<File> = arrayListOf(),
    var storageType: Int = 0,
    var isPublic: Boolean = false,
    var fileMimeType: String = ""
)