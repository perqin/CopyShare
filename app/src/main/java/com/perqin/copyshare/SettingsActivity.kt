package com.perqin.copyshare

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import android.support.v7.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager
                .beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()
    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            addPreferencesFromResource(R.xml.pref_settings)

            enableServicePreference.setOnPreferenceChangeListener { _, checkedObj ->
                if (checkedObj as Boolean) {
                    activity.startService(Intent(activity, CopyListenerService::class.java))
                } else {
                    activity.stopService(Intent(activity, CopyListenerService::class.java))
                }

                true
            }
        }

        override fun onResume() {
            super.onResume()
            enableServicePreference.isChecked = CopyListenerService.isStarted(activity)
        }

        val enableServicePreference by lazy {
            findPreference(getString(R.string.pk_enable_service)) as SwitchPreference
        }
    }
}
