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

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P || (Build.VERSION.SDK_INT == Build.VERSION_CODES.P && Build.VERSION.PREVIEW_SDK_INT != 0)) {
            // Not working so far. Might adapt to some workaround
            setContentView(R.layout.activity_settings_not_compatible)
            return
        }

        supportFragmentManager
                .beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.pref_settings)
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

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            // In case the app is forced stopped before
            if (enableServicePreference.isChecked) {
                activity!!.startService(Intent(activity, CopyListenerService::class.java))
            }
        }

        private val enableServicePreference by lazy {
            findPreference<SwitchPreference>(getString(R.string.pk_enable_service))!!
        }
        private val headsUpNotificationPreference by lazy {
            findPreference<SwitchPreference>(getString(R.string.pk_heads_up_notification))!!
        }
    }
}
