package com.employeeDummy

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.employeeDummy.data.Employee

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val employee = if (Build.VERSION.SDK_INT >= 33) {
            intent.getSerializableExtra("employee", Employee::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("employee") as? Employee
        }

        if (employee != null) {
            findViewById<TextView>(R.id.tvDetailName).text = employee.name
            findViewById<TextView>(R.id.tvDetailAge).text = employee.age.toString()
            findViewById<TextView>(R.id.tvDetailSalary).text = employee.salary.toString()
        }
    }

    // back navigation
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}