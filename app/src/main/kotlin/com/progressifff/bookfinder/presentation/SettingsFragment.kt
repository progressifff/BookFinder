package com.progressifff.bookfinder.presentation

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceScreen
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(com.progressifff.bookfinder.R.xml.settings, rootKey)
        val appVersionPref = findPreference("AppVersion")
        try {
            val packageInfo = activity!!.packageManager.getPackageInfo(activity!!.packageName, 0)
            appVersionPref.summary = packageInfo.versionName

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view!!.setBackgroundColor(Color.WHITE)
        return view
    }

    override fun setPreferenceScreen(preferenceScreen: PreferenceScreen?) {
        super.setPreferenceScreen(preferenceScreen)
        if (preferenceScreen != null) {
            val count = preferenceScreen.preferenceCount
            for (i in 0 until count) {
                preferenceScreen.getPreference(i)!!.isIconSpaceReserved = false
            }
        }
    }
}