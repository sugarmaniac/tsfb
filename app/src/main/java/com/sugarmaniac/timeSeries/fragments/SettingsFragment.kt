package com.sugarmaniac.timeSeries.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sugarmaniac.AlertReceiver
import com.sugarmaniac.timeSeries.R
import java.util.*
import kotlin.collections.HashSet

class SettingsFragment : PreferenceFragmentCompat() {
    lateinit var alarmManager: AlarmManager

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey)
        val pref = findPreference<MultiSelectListPreference>("TargetDays")
        pref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener(function = {
                    preference, newValue ->
                    cancelAlarms()
                    setNotifications(newValue)
                true
            })

    }

    private fun setNotifications(newValue: Any?) {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 20)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val days = newValue as HashSet<String>

        for (i in 1..7){
            if (days.contains(""+i)){
                calendar.set(Calendar.DAY_OF_WEEK, i)
                val intent = Intent(requireActivity(), AlertReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(requireActivity(), i, intent, PendingIntent.FLAG_IMMUTABLE)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY*7 ,pendingIntent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initAlarmManager()
    }

    private fun initAlarmManager() {
        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun cancelAlarms(){
        for (i in 1..7){
            val intent = Intent(requireActivity(), AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(requireActivity(), i, intent, PendingIntent.FLAG_IMMUTABLE)
            alarmManager.cancel(pendingIntent)
        }
    }
}