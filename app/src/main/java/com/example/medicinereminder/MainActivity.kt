package com.example.medicinereminder

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() , RclrClicked , DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    lateinit var rclrView : RecyclerView
    lateinit var addButton: FloatingActionButton
    lateinit var viewModel : ViewModell
    var date = 0
    var month = 0
    var year = 0
    var hour = 0
    var min = 0
    var savedDate = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMin = 0
    var savedDateUntil = 0
    var savedMonthUntil = 0
    var savedYearUntil = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rclrView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)

        val cal : Calendar = Calendar.getInstance()
        date = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        min = cal.get(Calendar.MINUTE)



        rclrView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(this , this)
        rclrView.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(ViewModell::class.java)
        viewModel.allNotes.observe(this, Observer {list->

            list?.let {
                adapter.updateList(it)
            }
        })

        addButton.setOnClickListener {

            val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.dialoguebox, null)
            val medicineName = view.findViewById<EditText>(R.id.editTextMedicine)
            val dose = view.findViewById<EditText>(R.id.medicinesEachDay)
            val timeOfFirstDose = view.findViewById<Button>(R.id.Time)
            val medicineYouHave = view.findViewById<Button>(R.id.MedicineYouHave)
            val medicineUntil = view.findViewById<Button>(R.id.MedicineUntil)
            val submitButton = view.findViewById<Button>(R.id.submitButton)



////aler dialogue
            val alertDialog = AlertDialog.Builder(this@MainActivity)
                .setView(view).setCancelable(false).create()
            alertDialog.show()

 //////recycler view
            timeOfFirstDose.setOnClickListener {
                TimePickerDialog(this,this,hour,min,false).show()
                 }
            medicineYouHave.setOnClickListener {
                DatePickerDialog(this,this,year,month,date).show()
            }
            medicineUntil.setOnClickListener {
                DatePickerDialog(this,this,year,month,date).show()
            }
            submitButton.setOnClickListener {

                var name :String = medicineName.text.toString()
                var dose : String = dose.text.toString()

                if(name.isNotEmpty() && dose.isNotEmpty() ){
                   var dosee : Int = dose.toInt()
                    viewModel.insertNote(UserEntity(name,dosee))
                }
                alertDialog.hide()
            }
        }


    }

    override fun itemClicked(data: UserEntity) {

        viewModel.deleteNote(data)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        if(savedDate == 0){
        savedDate = dayOfMonth
        savedMonth = month
        savedYear = year}
        else {
            savedDateUntil = dayOfMonth
            savedMonthUntil = month
            savedYearUntil = year
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hour
        savedMin = min
    }


}