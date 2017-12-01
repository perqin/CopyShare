package com.perqin.copyshare

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

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
            headsUpNotificationPreference.setOnPreferenceChangeListener { _, checkedObj ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && checkedObj as Boolean) {
                    Toast.makeText(activity, R.string.toast_you_have_to_disable_the_sound_manually, Toast.LENGTH_SHORT).show()
                }
                true
            }
        }

        override fun onResume() {
            super.onResume()
            enableServicePreference.isChecked = CopyListenerService.isStarted(activity)
        }

        private val enableServicePreference by lazy {
            findPreference(getString(R.string.pk_enable_service)) as SwitchPreference
        }
        private val headsUpNotificationPreference by lazy {
            findPreference(getString(R.string.pk_heads_up_notification)) as SwitchPreference
        }
    }
}
