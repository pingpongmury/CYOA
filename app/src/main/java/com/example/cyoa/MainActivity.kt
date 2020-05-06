package com.example.cyoa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

// This activity displays a list of stories for the user to select from
class MainActivity : AppCompatActivity() {
    companion object{
        // Initialize library
        var myLib: Library = Library()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myLib = Library(applicationContext, "library.txt")

        //Initialize story list recyclerView
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = MainAdapter()
    }
}
