package com.example.android.accessibility.runapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_timer.*
import java.util.*


class TimerActivity : AppCompatActivity() {
    var time = 0
    var started = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.android.accessibility.runapp.R.layout.activity_timer)
    }

    fun View.goHome() {
        val intent = Intent(this@TimerActivity, MainActivity::class.java)
        startActivity(intent)
    }

    fun View.startTimer() {
        findViewById<ImageButton>(R.id.start_stop_icon).setImageResource(R.drawable.ic_action_name)
        runTimer()
    }

    fun View.reset() {
        time = 0
        started = false
        updateText()
    }

    private fun pause() {
        started = false
        start_label.text = "Start"
        findViewById<ImageButton>(R.id.start_stop_icon).setImageResource(R.drawable.ic_start_button)
    }

    fun updateText() {
        val minutes: Int = time / 360000
        val secs: Int = time % 6000 / 100
        val milli: Int = time % 100
        var timeString: String = java.lang.String
            .format(
                Locale.getDefault(),
                "%02d:%02d:%02d",
                minutes, secs, milli
            )
        timer_text.text = timeString
    }

    private fun runTimer() {
        if (started) {
            return pause()
        }
        start_label.text = "Pause"
        started = true
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                updateText()
                if (started) {
                    time += 1
                    handler.postDelayed(this, 10)
                }
            }
        })
    }
}