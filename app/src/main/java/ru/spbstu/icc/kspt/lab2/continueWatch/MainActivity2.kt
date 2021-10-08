package ru.spbstu.icc.kspt.lab2.continueWatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    var active: Boolean = true
    private lateinit var sharedPref: SharedPreferences

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            if (active) {
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "${secondsElapsed++}"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed.text = getString(R.string.seconds, secondsElapsed)
        sharedPref = getSharedPreferences(SECONDS, Context.MODE_PRIVATE)
        backgroundThread.start()
    }

    override fun onStop() {
        active = false
        val editor = sharedPref.edit()
        editor.putInt(SECONDS, secondsElapsed)
        editor.apply()
        super.onStop()
    }

    override fun onResume() {
        active = true
        secondsElapsed = sharedPref.getInt(SECONDS, 0)
        super.onResume()
    }

    companion object { const val SECONDS = "Seconds" }
}