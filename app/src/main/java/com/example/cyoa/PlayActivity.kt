package com.example.cyoa
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_play.*

// This activity:
//      1. Displays the title of the selected story in the navigation bar
//      2. Displays the story prompts with their associated responses
class PlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        // Holds the Id of the prompt TextView for access in the PlayAdapter class
        val myPrompt = findViewById<TextView>(R.id.story_prompt)

        // Receive the selected story passed from MainAdapter -> StoryListViewHolder
        val storyPos = intent.getIntExtra(StoryListViewHolder.STORY_KEY,0)
        val resumePos: Int = intent.getIntExtra(StoryListViewHolder.RESUME_KEY,0)

        // Initialize response list
        recyclerView_play.layoutManager = LinearLayoutManager(this)
        recyclerView_play.adapter = PlayAdapter(storyPos,resumePos, myPrompt)

        // Populate navigation bar with selected story title
        val navBarTitle = MainActivity.myLib.library[storyPos].title
        supportActionBar?.title = navBarTitle
        supportActionBar?.setHomeActionContentDescription("Return to Library")
    }
}