package com.cos30017.task3

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        //Retrieve history
        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val lastCountry = sharedPref.getString("last_country", "No history available")

        // Display the history in a TextView
        findViewById<TextView>(R.id.textViewHistory).text = "The last country clicked was: $lastCountry"
    }
}
