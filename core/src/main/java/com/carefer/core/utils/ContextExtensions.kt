package com.carefer.core.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.provider.Settings
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.carefer.core.R
import java.util.Locale


fun Context.startBrowserIntent(url: String) {
    if (url.isEmpty()) {
        return
    }
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    this.startActivity(i)
}

/**
 * Extension function to get int value of color from color resource.
 *
 * @receiver Context
 * @param resourceId Int the color res.
 * @return Int value of the color.
 */
fun Context.getColorCompat(@ColorRes resourceId: Int) = ContextCompat.getColor(this, resourceId)

/**
 * Extension function to get drawable from drawable resource.
 *
 * @receiver Context
 * @param resourceId Int the drawable res
 * @return Drawable? the instance of drawable
 */
fun Context.getDrawableCompat(@DrawableRes resourceId: Int) =
    ContextCompat.getDrawable(this, resourceId)

fun Context.makeCall(activity: Activity, phoneNumber: String, callPhonePermissionRequestCode: Int) {
    val callIntent = Intent(Intent.ACTION_CALL)
    callIntent.data = Uri.parse("tel:$phoneNumber")
    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        )
        != PackageManager.PERMISSION_GRANTED
    ) {

        activity.requestPermissions(
            arrayOf(Manifest.permission.CALL_PHONE),
            callPhonePermissionRequestCode
        )

    } else {
        startActivity(callIntent)
    }
}

fun Context.sendMail(emailAddress: String) {
    val mIntent = Intent(Intent.ACTION_SEND)
    mIntent.data = Uri.parse("mailto:")
    mIntent.type = "text/plain"
    mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
    startActivity(mIntent)

}

fun Context.openAppSettings() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    intent.addCategory(Intent.CATEGORY_DEFAULT)
    intent.data = Uri.parse("package:" + this.packageName)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    this.startActivity(intent)
}


fun Context.addToContactNumber(name: String, mobile: String, emailAddress: String) {
    val intent = Intent(Intent.ACTION_INSERT)
    intent.type = ContactsContract.Contacts.CONTENT_TYPE
    intent.putExtra(ContactsContract.Intents.Insert.NAME, name)
    intent.putExtra(ContactsContract.Intents.Insert.PHONE, mobile)
    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, emailAddress)
    startActivity(intent)
}

fun Context.launchShareIntent(shareUrl: String, title: String = "", message: String = "") {
    val intent = Intent(Intent.ACTION_SEND)
    intent.apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TITLE, title,
        )
        putExtra(Intent.EXTRA_TEXT, shareUrl)
    }
    this.startActivity(Intent.createChooser(intent, this.getString(R.string.share_using)))
}

fun Context.addEventToCalendar(
    eventStartTimeMilliSeconds: Long,
    eventEndTimeMilliSeconds: Long,
    eventTitle: String,
    eventLocation: String,
    isAllDAY: Boolean
) {
    val intent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, eventStartTimeMilliSeconds)
        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, eventEndTimeMilliSeconds)
        .putExtra(CalendarContract.Events.TITLE, eventTitle)
        .putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation)
        .putExtra(CalendarContract.Events.ALL_DAY, isAllDAY)
        .putExtra(
            CalendarContract.Events.AVAILABILITY,
            CalendarContract.Events.AVAILABILITY_BUSY
        )
    startActivity(intent)
}

fun Context.getStringByLocale(
    @StringRes stringRes: Int,
    locale: Locale,
    vararg formatArgs: Any
): String {
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)
    return createConfigurationContext(configuration).resources.getString(stringRes, *formatArgs)
}

fun Context.getLocal(context: Context): Locale {
    return if (LocaleUtils.getPersistedData(
            context,
            Constants.ENGLISH_CODE
        ) == Constants.ENGLISH_CODE
    ) {
        Locale("en")
    } else {
        Locale("ar")
    }
}

