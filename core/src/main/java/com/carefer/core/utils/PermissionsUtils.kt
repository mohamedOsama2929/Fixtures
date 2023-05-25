package com.carefer.core.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.carefer.core.R


fun Activity.showRationaleDialog() {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setCancelable(false)
    val customView: View =
        LayoutInflater.from(this).inflate(R.layout.layout_permission_rationale, null)
    dialogBuilder.setView(customView)
    val alertDialog: AlertDialog = dialogBuilder.create()
    val okayButton: TextView = customView.findViewById(R.id.tvOk)
    val cancelButton: TextView = customView.findViewById(R.id.tvCancel)
    okayButton.setOnClickListener {
        openAppSettings()
        alertDialog.dismiss()
    }
    cancelButton.setOnClickListener {
        alertDialog.dismiss()
    }
    alertDialog.show()
}

fun Activity.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:" + this.packageName)
    startActivity(intent)
}

