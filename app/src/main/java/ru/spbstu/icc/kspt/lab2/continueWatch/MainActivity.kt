package ru.spbstu.icc.kspt.lab2.continueWatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView

    var MyThread : Boolean = true

    var backgroundThread = Thread {
        while (true) {
            if (MyThread) secondsElapsed++
            textSecondsElapsed.post {
                textSecondsElapsed.text = getString(R.string.seconds, secondsElapsed)
            }
            Thread.sleep(1000)
        }
    }

    override fun onResume() {
        super.onResume()
        MyThread = true
    }

    override fun onRestart() {
        super.onRestart()
        MyThread = true
    }

    override fun onPause() {
        super.onPause()
        MyThread = false
    }

    override fun onStop() {
        super.onStop()
        MyThread = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{putInt(SECONDS, secondsElapsed)}
        super.onSaveInstanceState(outState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run{secondsElapsed = getInt(SECONDS)}
    }


    companion object { const val SECONDS = "Seconds" }

}
