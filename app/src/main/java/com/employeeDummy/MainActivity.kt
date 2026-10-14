package com.employeeDummy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.employeeDummy.data.Employee
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: EmployeeAdapter

    private val jsonUrl = "https://aamras.com/dummy/EmployeeDetails.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        progressBar = findViewById(R.id.progressBar)

        adapter = EmployeeAdapter(this)
        listView.adapter = adapter

        // Tap a row to open DetailActivity with that employee
        listView.setOnItemClickListener { _, _, position, _ ->
            val employee = adapter.getItem(position)
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("employee", employee)
            startActivity(intent)
        }

        fetchEmployees()
    }

    private fun fetchEmployees() {
        // Network work must NOT run on the main thread
        Thread {
            try {
                val connection = URL(jsonUrl).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                connection.disconnect()


                val root = JSONObject(response)
                val array = root.getJSONArray("employees")

                val list = mutableListOf<Employee>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        Employee(
                            name = obj.getString("name"),
                            age = obj.getInt("age"),
                            salary = obj.getInt("salary")
                        )
                    )
                }

                // Back to the UI thread to update the screen
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    adapter.clear()
                    adapter.addAll(list)
                }
            } catch (e: Exception) {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }
}