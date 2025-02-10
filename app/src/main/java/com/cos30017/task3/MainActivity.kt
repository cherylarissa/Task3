package com.cos30017.task3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cos30017.task3.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var medallistAdapter: MedallistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the custom Toolbar as the ActionBar
        setSupportActionBar(binding.myToolbar)

        val teams = readCsvFile("medallists.csv")

        // Calculate top 10 countries based on total medals
        val top10Countries = teams.sortedByDescending {
            it.gold + it.silver + it.bronze
        }.take(10).map { it.country }.toSet()

        // Initialize the adapter with both short and long press listeners
        medallistAdapter = MedallistAdapter(
            teams,
            top10Countries,
            onItemShortPressed = { medallist ->
                val totalMedals = medallist.gold + medallist.silver + medallist.bronze
                Toast.makeText(this, "Total Medals: $totalMedals", Toast.LENGTH_SHORT).show()
                saveToSharedPreferences("${medallist.country} (${medallist.iocCode})")
            },
            onItemLongPressed = { medallist ->
                saveToSharedPreferences("${medallist.country} (${medallist.iocCode})")
                showBottomSheetDialog(medallist)
            }
        )

        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = medallistAdapter
        }
    }

    private fun showBottomSheetDialog(medallist: Medallist) {
        val bottomSheet = MedallistDetailsBottomSheet.newInstance(medallist)
        bottomSheet.show(supportFragmentManager, "MedallistDetailsBottomSheet")
    }

    private fun saveToSharedPreferences(history: String) {
        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().putString("last_country", history).apply()
    }

    private fun readCsvFile(fileName: String): List<Medallist> {
        val medallistList = mutableListOf<Medallist>()
        try {
            val inputStream = assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.readLine()  // Skip header line

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val tokens = line!!.split(",")
                if (tokens.size == 6) {
                    val medallist = Medallist(
                        country = tokens[0].trim(),
                        iocCode = tokens[1].trim(),
                        timesCompeted = tokens[2].trim().toInt(),
                        gold = tokens[3].trim().toInt(),
                        silver = tokens[4].trim().toInt(),
                        bronze = tokens[5].trim().toInt()
                    )
                    medallistList.add(medallist)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return medallistList
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.homemenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ItemHistory -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
