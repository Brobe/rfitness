package com.example.rfitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.LinearLayout
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<LinearLayout>(R.id.button_layout)
        
        findViewById<Button>(R.id.settingsButton).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        button?.setOnClickListener() {
            // Toast.makeText(this@MainActivity, "Test", Toast.LENGTH_LONG).show()
            val intent = Intent(this, GvtActivity::class.java)
            startActivity(intent)
        }
    }
}

class GvtActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gvt)

        val button = findViewById<Button>(R.id.button)

        button?.setOnClickListener {
            val intent = Intent(this, GvtWorkoutActivity::class.java)
            startActivity(intent)
        }
    }

}

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val button = Button(this).apply {
            text = "Show workout history"
            setOnClickListener {
                startActivity(
                    Intent(this@SettingsActivity, WorkoutHistoryActivity::class.java)
                )
            }
        }
        setContentView(button)
    }
}
