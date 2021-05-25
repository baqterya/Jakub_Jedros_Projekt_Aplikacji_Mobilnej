package com.example.quizapp.view.fragment

import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import com.example.quizapp.alarm.AlarmReceiver
import com.example.quizapp.databinding.FragmentSettingsBinding
import java.util.*

class SettingsFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    lateinit var binding: FragmentSettingsBinding

    private var mHour = 0
    private var mMinute = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.settingsAlarmTimePickerButton.setOnClickListener {
            getCurrentTime()
            TimePickerDialog(requireContext(), this, mHour, mMinute, DateFormat.is24HourFormat(requireContext())).show()
        }

        return binding.root
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        mHour = hourOfDay
        mMinute = minute

        schedulePushNotifications()

        Toast.makeText(requireContext(), "$mHour:$mMinute", Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentTime() {
        val c = Calendar.getInstance()
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)
    }

    private fun schedulePushNotifications() {
        val alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        val calendar = GregorianCalendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, mHour)
            set(Calendar.MINUTE, mMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent
        )

    }


}

