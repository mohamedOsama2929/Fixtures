package com.carefer.core.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import com.carefer.core.utils.LocaleUtils.ARABIC_LANG_CODE
import java.util.Locale


class LanguageUtils {
    companion object {

        const val ENGLISH_CODE = "en"
        const val ARABIC_CODE = "ar"

        fun getCurrentLanguage(
            context: Context,
            defaultLanguage: String = ENGLISH_CODE
        ): String {
            return LocaleUtils.getPersistedData(context, Constants.ARABIC_CODE) ?: defaultLanguage
        }

        fun changeAppLanguage(context: Context) {
            val appLanguage = LocaleUtils.getPersistedData(context, Constants.ARABIC_CODE)
            if (appLanguage == ARABIC_CODE) {
                LocaleUtils.setLocale(context, LocaleUtils.ENGLISH_LANG_CODE)
                setLanguage(context, ARABIC_CODE)
            } else {
                LocaleUtils.setLocale(context, ARABIC_LANG_CODE)
                setLanguage(context, ENGLISH_CODE)
            }

        }

        fun setLocale(context: Context) {
            val appLanguage = getCurrentLanguage(context)
            /* LocaleUtils.setLocale(context, LocaleUtils.ARABIC_LANG_CODE)
             if (appLanguage == ENGLISH_CODE) {
                 LocaleUtils.setLocale(context, LocaleUtils.ENGLISH_LANG_CODE)
             } else {
                 LocaleUtils.setLocale(context, LocaleUtils.ARABIC_LANG_CODE)
             }*/
            setLanguage(context, appLanguage)
        }

        fun setLanguage(context: Context, lang: String) {
            val mLocale: Locale = if (lang == ARABIC_LANG_CODE)
                Locale("ar")
            else
                Locale("en")

            Locale.setDefault(mLocale)
            val config = context.resources.configuration

            if (config.locale != mLocale) {
                config.locale = mLocale
                config.setLayoutDirection(config.locale)
                context.resources.updateConfiguration(config, null)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    (context as Activity).window.decorView.layoutDirection =
                        if (Locale.getDefault().language.equals("ar", ignoreCase = true))
                            View.LAYOUT_DIRECTION_RTL
                        else
                            View.LAYOUT_DIRECTION_LTR
                }
            }
        }


        fun getLanguage() =
            if (Locale.getDefault().language.contains("ar", true)) "ar" else "en"
    }
}