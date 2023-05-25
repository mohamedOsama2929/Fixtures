package com.carefer.core.domain.utils

import android.annotation.SuppressLint
import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import com.carefer.core.domain.entities.enums.DateDifference
import com.carefer.core.domain.entities.enums.DateType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import java.time.chrono.HijrahChronology
import java.time.chrono.HijrahDate
import java.time.chrono.HijrahEra
import java.util.*


fun convertDateStringToMilliSeconds(
    dateFromApi: String,
    inputDateTemplate: String
): Long {
    try {
        val inputSimpleDateFormat = SimpleDateFormat(inputDateTemplate, Locale.US)
        inputSimpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val apiDate = inputSimpleDateFormat.parse(dateFromApi) ?: Date()
        val calendar = Calendar.getInstance()
        calendar.time = apiDate
        calendar.timeZone = TimeZone.getDefault()
        return calendar.time.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return 0
}

fun changeDateFormat(
    dateFromApi: String,
    inputDateTemplate: String,
    outputDateTemplate: String
): String {
    return try {
        val inputSimpleDateFormat = SimpleDateFormat(inputDateTemplate, Locale.US)
        inputSimpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val apiDate = inputSimpleDateFormat.parse(dateFromApi) ?: Date()
        val outputSimpleDateFormat = SimpleDateFormat(outputDateTemplate, Locale.getDefault())
        outputSimpleDateFormat.timeZone = TimeZone.getDefault()
        outputSimpleDateFormat.format(apiDate)
    } catch (e: Exception) {
        ""
    }
}

fun changeDateFormatForUpcomingHolidays(
    dateFromApi: String,
    inputDateTemplate: String,
    outputDateTemplate: String
): String {
    return try {
        val inputSimpleDateFormat = SimpleDateFormat(inputDateTemplate, Locale.getDefault())
        inputSimpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val apiDate = inputSimpleDateFormat.parse(dateFromApi) ?: Date()
        val outputSimpleDateFormat = SimpleDateFormat(outputDateTemplate, Locale.US)
        outputSimpleDateFormat.timeZone = TimeZone.getDefault()
        outputSimpleDateFormat.format(apiDate)
    } catch (e: Exception) {
        ""
    }
}

fun changeDateFormatLocale(
    dateFromApi: String,
    inputDateTemplate: String,
    outputDateTemplate: String
): String {
    return try {
        val inputSimpleDateFormat = SimpleDateFormat(inputDateTemplate, Locale.US)
        inputSimpleDateFormat.timeZone = TimeZone.getDefault()
        val apiDate = inputSimpleDateFormat.parse(dateFromApi) ?: Date()
        val outputSimpleDateFormat = SimpleDateFormat(outputDateTemplate, Locale.US)
        outputSimpleDateFormat.timeZone = TimeZone.getDefault()
        outputSimpleDateFormat.format(apiDate)
    } catch (e: Exception) {
        ""
    }
}

fun convertDateToStringDate(date: Date, outputDateFormat: String): String {
    return try {
        val outputSimpleDateFormat = SimpleDateFormat(outputDateFormat, Locale.US)
        outputSimpleDateFormat.timeZone = TimeZone.getDefault()
        outputSimpleDateFormat.format(date)
    } catch (e: Exception) {
        ""
    }
}

fun convertDateToStringDateLocalized(date: Date, outputDateFormat: String): String {
    return try {
        val outputSimpleDateFormat = SimpleDateFormat(outputDateFormat, Locale.getDefault())
        outputSimpleDateFormat.timeZone = TimeZone.getDefault()
        outputSimpleDateFormat.format(date)
    } catch (e: Exception) {
        ""
    }
}

fun convertDateToRelativeDate(
    dateStr: String,
    dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss"
): String {

    val inputSimpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)
    inputSimpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val date = inputSimpleDateFormat.parse(dateStr) ?: Date()

    return DateUtils.getRelativeTimeSpanString(
        date.time,
        Calendar.getInstance().timeInMillis,
        DateUtils.SECOND_IN_MILLIS
    ).toString()
}

@SuppressLint("SimpleDateFormat")
fun convertDateToStringDate(
    calender: Calendar,
    outputDateFormat: String,
    locale: Locale = Locale.getDefault()
): String {
    return try {
        SimpleDateFormat(outputDateFormat, locale).format(calender.timeInMillis)
    } catch (e: Exception) {
        ""
    }
}

fun getDateDifference(
    start_date: String = "",
    end_date: String = "",
    dateDifference: DateDifference,
    dateFormat: String
): String {

    val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())

    try {
        val d1 = sdf.parse(start_date)
        val d2 = sdf.parse(end_date)
        // Calculate time difference in milliseconds
        val differenceInTime = d2.time - d1.time

        return when (dateDifference) {
            DateDifference.SECONDS -> ((differenceInTime / 1000) % 60).toString()
            DateDifference.MINUTES -> ((differenceInTime / (1000 * 60)) % 60).toString()
            DateDifference.HOURS -> ((differenceInTime / (1000 * 60 * 60)) % 24).toString()
            DateDifference.DAYS -> ((differenceInTime / (1000 * 60 * 60 * 24)) % 365).toString()
            DateDifference.YEARS -> (differenceInTime / (1000L * 60 * 60 * 24 * 365)).toString()
        }
    } catch (e: ParseException) {
        e.printStackTrace()
        return ""
    }
}

fun getCurrentDate(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}

fun getCurrentDateFormatted(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Date())
}


fun getEndDateOfTheYearFormatted(): String {
    // Create last day of year
    val lastDayOfCurrentYear = Calendar.getInstance()
    lastDayOfCurrentYear[Calendar.DATE] = 31
    lastDayOfCurrentYear[Calendar.MONTH] = 11
    return convertDateToStringDate(lastDayOfCurrentYear, "dd/MM/yyyy", Locale.US)
}

fun getDateByType(date: String, dateType: DateType, regex: String): Int {
    val parsedDate = date.split(regex)
    return when (dateType) {
        DateType.DAY -> parsedDate[0].toInt()
        DateType.MONTH -> parsedDate[1].toInt()
        DateType.YEAR -> parsedDate[2].toInt()
    }
}

fun getAddedDate(date: String, amount: Int): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val c = Calendar.getInstance()
    c.time = sdf.parse(date)
    c.add(Calendar.DATE, amount) // number of days to add
    return sdf.format(c.time)
}

fun getLastDayOfMonthDate(month: Int): String {
    val calendar = Calendar.getInstance()
    calendar[Calendar.MONTH] = month - 1
    calendar[Calendar.DATE] = calendar.getActualMaximum(Calendar.DATE)
    val date = calendar.time
    val dateFormat = SimpleDateFormat("dd/MM", Locale.US)
    return dateFormat.format(date)
}

fun getCurrentYear(): Int {
    return Year.now().value
    /*val now = Calendar.getInstance()
    val year = now[Calendar.YEAR]
    return year*/
}

fun convertDateStringToMilliSeconds(date: String): Long {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val convertedDate = sdf.parse(date)
    return convertedDate.time
}

fun changeDateToFormat(outPutFormat: String, date: Date): String {
    val outputSimpleDateFormat = SimpleDateFormat(outPutFormat, Locale.US)
    return outputSimpleDateFormat.format(date)
}


fun changeDateToEnglishFormat(outPutFormat: String, date: Date): String {
    val outputSimpleDateFormat = SimpleDateFormat(outPutFormat, Locale.ENGLISH)
    return outputSimpleDateFormat.format(date)
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertHijriToGregorianCalendar(hijrahDate: String): String {
    val day = changeDateFormatLocale(hijrahDate, "dd/MM/y HH:mm:ss", "dd").toInt()
    val month = changeDateFormatLocale(hijrahDate, "dd/MM/y HH:mm:ss", "M").toInt()
    val year = "14${changeDateFormatLocale(hijrahDate, "dd/MM/y HH:mm:ss", "yy")}".toInt()
    val hd: HijrahDate = HijrahChronology.INSTANCE.date(HijrahEra.AH, year, month, day)
    val ld: LocalDate = LocalDate.from(hd)
    return ld.toString()
}

@SuppressLint("SimpleDateFormat")
fun convertTime(dateFromApi: String): String {
    val date = SimpleDateFormat("dd/MM/y HH:mm:ss").parse(dateFromApi)
    return SimpleDateFormat("HH:mm a").format(date)
}