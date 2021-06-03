package com.example.quizapp.view.fragment

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import com.example.quizapp.alarm.TimerExpiredReceiver
import com.example.quizapp.databinding.FragmentSettingsBinding
import java.util.*

class SettingsFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    lateinit var binding: FragmentSettingsBinding

    private var hourOfAlarm = 0
    private var minuteOfAlarm = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.settingsAlarmTimePickerButton.setOnClickListener {
            val c = Calendar.getInstance()
            hourOfAlarm = c.get(Calendar.HOUR_OF_DAY)
            minuteOfAlarm = c.get(Calendar.MINUTE)

            TimePickerDialog(
                requireContext(), this, hourOfAlarm, minuteOfAlarm, DateFormat.is24HourFormat(requireContext())
            ).show()
        }

        binding.settingsAlarmDisableButton.setOnClickListener {
            cancelTimer()
        }

        return binding.root
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        hourOfAlarm = hourOfDay
        minuteOfAlarm = minute
        setAlarm(requireContext(), hourOfAlarm, minuteOfAlarm)
        Toast.makeText(requireContext(), "Reminder set on $hourOfAlarm:$minuteOfAlarm", Toast.LENGTH_SHORT).show()
    }

    private fun cancelTimer() {
        removeAlarm(requireContext())
        Toast.makeText(requireContext(), "Reminder disabled", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun setAlarm(context: Context, hour: Int, minute: Int) {
            val date = Calendar.getInstance()
            date.set(Calendar.HOUR_OF_DAY, hour)
            date.set(Calendar.MINUTE, minute)
            date.set(Calendar.SECOND, 0)

            val time = (hour * 3.6e6 + minute * 60000).toLong()

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP, time, 60000, pendingIntent
                //AlarmManager.INTERVAL_DAY
            )
        }

        fun removeAlarm(context: Context) {
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }
    }
}

