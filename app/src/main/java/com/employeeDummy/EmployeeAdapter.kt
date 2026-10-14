package com.employeeDummy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.employeeDummy.data.Employee

class EmployeeAdapter(context: Context) : ArrayAdapter<Employee>(context, 0) {

    // Called for every visible row; builds (or reuses) the row view
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.list_item_employee, parent, false)

        val employee = getItem(position)!!
        view.findViewById<TextView>(R.id.tvName).text = employee.name
        view.findViewById<TextView>(R.id.tvAge).text = employee.age.toString()
        view.findViewById<TextView>(R.id.tvSalary).text = employee.salary.toString()
        return view
    }
}