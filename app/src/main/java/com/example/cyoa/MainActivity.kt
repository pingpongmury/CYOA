package com.example.cyoa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

// This activity displays a list of stories for the user to select from
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize library
        val myLib: Library = Library(applicationContext, "library.txt")

        //Initialize story list recyclerView
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = MainAdapter(myLib)

        //TODO: add story items to recyclerview             DONE
        //TODO: make recyclerview clickable                 DONE
        //TODO: link clicks to new activity (play activity) DONE
        //TODO: add prompt items to play activity           DONE
        //TODO: add response items to play activity         DONE
        //TODO: fix PlayAdapter.getItemCount()              DONE
        //TODO: implement game logic                        DONE
        //TODO: make it pretty
        //TODO: make it accessible to the blind
        //TODO: if time:
        //TODO:     clean up
        //TODO:     add exception handling
        //TODO:     add scope modifiers where appropriate
    }
}
