package com.example.cyoa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_play.view.*
import kotlinx.android.synthetic.main.story_response.view.*

// This Adapter:
//      1. Populates the prompt TextView and response list appropriately
//      2. Makes items in the response list recyclerView clickable
//      3. Links the clicked response to the appropriate new prompt/responses pair
//      4. Determines the Win/Lose outcome of the player's session
class PlayAdapter(
    private val storyPos: Int,
    private val resumePos: Int,
    private val myPrompt: TextView) : RecyclerView.Adapter<ResponseListViewHolder>() {

    private var promptIndex: Int = resumePos
    private var currentStory: Story = MainActivity.myLib.library[storyPos]

    // When a response item is created:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val responseListItem = layoutInflater.inflate(R.layout.story_response, parent, false)

        // Return the response item to the response list recyclerView
        return ResponseListViewHolder(responseListItem)
    }

    //How many responses are in the list?
    override fun getItemCount(): Int {
        return currentStory.prompts[promptIndex].responses.size
    }

    // Binds response data to the response item,
    // Updates prompt and responses when response is clicked
    // Determines win/loss status
    override fun onBindViewHolder(holder: ResponseListViewHolder, position: Int) {
        myPrompt.story_prompt.text = currentStory.prompts[promptIndex].text
        holder.view.story_response.text = currentStory.prompts[promptIndex].responses[position].text

        // Detect when response item is clicked,
        // When clicked: update prompt and response list recyclerView according to clicked response
        holder.view.setOnClickListener {

            // Update current position of the prompt vector
            promptIndex = currentStory.prompts[promptIndex].responses[position].goesTo
            currentStory.resumePosition = promptIndex

            // Holds the win/loss status at the updated prompt
            val gameStatus: Char = currentStory.prompts[promptIndex].winLoss

            // Determine win/loss status
            if(gameStatus != ' '){
                if(gameStatus == 'W'){ // WIN Case
                    Toast.makeText(holder.view.context,"YOU WIN!",Toast.LENGTH_SHORT).show()

                }else if(gameStatus == 'L'){ // LOSS Case
                    Toast.makeText(holder.view.context,"YOU LOSE!",Toast.LENGTH_SHORT).show()
                }
            }
            // Update the prompt and response list recyclerView
            notifyDataSetChanged()
            myPrompt.story_prompt.text = currentStory.prompts[promptIndex].text
            // Refocus TalkBack Accessibility Service to the prompt view
            myPrompt.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        }
    }

}



// Defines the story item and describes it's behavior
class ResponseListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {}
