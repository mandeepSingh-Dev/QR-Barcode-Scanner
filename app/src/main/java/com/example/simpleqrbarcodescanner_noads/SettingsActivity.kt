package com.example.simpleqrbarcodescanner_noads

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.simpleqrbarcodescanner_noads.Util.Intent_KEYS

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.settings, SettingsFragment()).commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat()
    {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val sharedPreferences  = activity?.getSharedPreferences(Intent_KEYS.SETTINGS_SHAREDPREFERNCE, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()

            val darkModePreference = findPreference<SwitchPreference>(getString(R.string.DarkKey))
            val AutomativalyOpenPrefernce = findPreference<SwitchPreference>(getString(R.string.AutomaticallyOpenKey))
            val AutomaticallyCopyPreference = findPreference<SwitchPreference>(getString(R.string.AutomaticallyCopyKey))
            val BeepPreference = findPreference<SwitchPreference>(getString(R.string.BeepKey))
            val VibratePreference = findPreference<SwitchPreference>(getString(R.string.VibrateKey))
            val PrivacyPreference = findPreference<Preference>(getString(R.string.PrivacyKey))
            val OpensourcePreference = findPreference<Preference>(getString(R.string.OpenSourceKey))

            darkModePreference?.setOnPreferenceChangeListener(object: Preference.OnPreferenceChangeListener{
                override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {

                    if(newValue.toString().equals("true"))
                    {
                        Toast.makeText(requireContext(),"Dark Theme Enabled",Toast.LENGTH_SHORT).show()

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    else{
                        Toast.makeText(requireContext(),"Dark Theme Disabled",Toast.LENGTH_SHORT).show()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    return true
                }
            })

            AutomativalyOpenPrefernce?.setOnPreferenceChangeListener(object: Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {

                    editor?.putString(Intent_KEYS.ISAUTOMATICLAYOPEN, newValue.toString())
                    editor?.apply()
                    // editor?.commit()
                    return true
                }
            })

            AutomaticallyCopyPreference?.setOnPreferenceChangeListener(object: Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {

                    editor?.putString(Intent_KEYS.ISAUTOMATICLAYCOPY, newValue.toString())
                    editor?.apply()
                    // editor?.commit()
                    return true
                }
            })

            BeepPreference?.setOnPreferenceChangeListener(object: Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {

                    editor?.putString(Intent_KEYS.ISBEEP, newValue.toString())
                    editor?.apply()
                    // editor?.commit()
                    return true
                }
            })

            VibratePreference?.setOnPreferenceChangeListener(object: Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {

                    editor?.putString(Intent_KEYS.ISVIBRATE, newValue.toString())
                    editor?.apply()
                    // editor?.commit()
                    return true
                }
            })

            PrivacyPreference?.setOnPreferenceClickListener {
                return@setOnPreferenceClickListener true
            }
            OpensourcePreference?.setOnPreferenceClickListener {
                return@setOnPreferenceClickListener true
            }

        }
    }
}
