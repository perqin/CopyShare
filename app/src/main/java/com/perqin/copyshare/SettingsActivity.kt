package com.perqin.copyshare

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
                .beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.pref_settings, null)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            enableServicePreference.setOnPreferenceChangeListener { _, checkedObj ->
                if (checkedObj as Boolean) {
                    activity!!.startService(Intent(activity, CopyListenerService::class.java))
                } else {
                    activity!!.stopService(Intent(activity, CopyListenerService::class.java))
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
            enableServicePreference.isChecked = CopyListenerService.isStarted(activity!!)
        }

        private val enableServicePreference by lazy {
            findPreference(getString(R.string.pk_enable_service)) as SwitchPreference
        }
        private val headsUpNotificationPreference by lazy {
            findPreference(getString(R.string.pk_heads_up_notification)) as SwitchPreference
        }
    }
}
