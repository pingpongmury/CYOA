package com.example.cyoa

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.story_info_item.view.*
import java.io.Serializable

// This adapter:
//      1. Populates the recyclerView with the list of stories / authors
//      2. Makes the items within the story list recyclerView clickable
//      3. Routes the user to the PlayActivity for the selected story
class MainAdapter(val myLib: Library): RecyclerView.Adapter<StoryListViewHolder>() {

    // When a story item is created:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val storyListItem = layoutInflater.inflate(R.layout.story_info_item, parent, false)

        // Return the story item to the story list recyclerView
        return StoryListViewHolder(storyListItem, myLib)
    }

    // How many stories are in the list?
    override fun getItemCount(): Int {
        return myLib.library.size
    }

    // Binds story data to the story item
    override fun onBindViewHolder(holder: StoryListViewHolder, position: Int) {
        holder.view.story_title_main.text = myLib.library[position].title
        holder.view.story_author_main.text = myLib.library[position].author
    }
}




// Defines the story item and describes it's behavior
class StoryListViewHolder(val view: View, var myLib: Library): RecyclerView.ViewHolder(view) {
    companion object {
        // Key to the SerializableExtra (Story Object) passed with Intent
        val STORY_KEY = "STORY"
    }

    init{
        // Detect when story item is clicked,
        // When clicked: pass selected story to a new PlayActivity
        view.setOnClickListener {
            val intent = Intent(view.context, PlayActivity::class.java)
            intent.putExtra(STORY_KEY, myLib.library[adapterPosition] as Serializable)
            view.context.startActivity(intent)
        }
    }
}