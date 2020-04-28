package com.example.cyoa

import android.content.Context
import java.util.*
import java.io.Serializable

// NOTE: The Serializable implementation allows the objects to be passed through an Intent
// NOTE: Files to be read by this database are to be formatted as follows:
/*
***************************************************************************************************
*************************************** STORY FILES ***********************************************
******************** (an example library is included as example_story.txt) ************************
***************************************************************************************************
storyTitle
storyAuthor
P
prompt1 text goes here
winLoss                                 // 'W' for win, 'L' for Loss - Include only if applicable (do not leave an empty line)
R-comesFrom-goesTo                      // Note the dashes, comesFrom and goesTo are integers, begin indexing at 1
response1 to prompt2 text goes here
R-comesFrom-goesTo
response2 to prompt1 text goes here
...                                     // Repeat for each response to prompt1
P
prompt2 text goes here
winLoss
R-comesFrom-goesTo
response1 to prompt 2 text goes here
R-comesFrom-goesTo
response2 to prompt 2 text goes here
...                                     // Repeat for each response to prompt2
...                                     // Repeat the format above for each prompt in the story

***************************************************************************************************
************************************** LIBRARY FILES **********************************************
******************** (an example library is included as example_library.txt) **********************
***************************************************************************************************
story1.txt                              // NOTE: stories can be titled however you like
story2.txt
story3.txt
...                                     // Repeat the format above for each story in the library

*/

// Holds the prompt, associated responses, and win/loss status
data class Prompt(
    var text: String = "",
    var responses: Vector<Response> = Vector(),
    var winLoss: Char = ' ') : Serializable{
}




// Holds the response and data to link it from/to a new prompt
data class Response(
    var text: String = "",
    var comesFrom: Int = -1,
    var goesTo: Int = -1) : Serializable{
}




// Holds the list of prompts and responses that make up the story, parses each from a file
class Story() : Serializable{
    var title: String = ""
    var author: String = ""
    var prompts: Vector<Prompt> = Vector()

    // Secondary constructor used to read and parse data from a .txt file
    constructor(myContext: Context, fileName: String): this(){

        // Holds the Prompt that is currently under construction
        val tempP: Prompt = Prompt()
        // Holds the Response that is currently under construction
        val tempR: Response = Response()

        // Parse the data from the given file
        try {
            // Holds the last line under observation
            var lastLine: String = String()
            // Holds the response identifier ('R') and linking data for the response ['R', comesFrom, goesTo]
            var fromTo: List<String>
            //
            val lines: List<String> = myContext.assets.open(fileName).bufferedReader().readLines()

            // Assign title and author
            title = lines[0]
            author = lines[1]

            // Parse prompts and responses
            // For each line of the story file
            for(line in lines){
                // Based on the line type identifier, classify the line as a: prompt ('P')
                //                                                            response ('R')
                //                                                            winLoss ('W' or 'L')
                if(lastLine == "P"){
                    if(tempP.responses.isNotEmpty() || tempP.winLoss != ' '){
                        prompts.addElement(tempP.copy(tempP.text, tempP.responses.clone() as Vector<Response>, tempP.winLoss))
                        tempP.responses.clear()
                        tempP.winLoss = ' '
                    }
                    tempP.text = line.replace("\\n", System.getProperty("line.separator").toString())
                }else if(lastLine.contains(Regex("R\\-\\d+\\-\\d+"))){
                    fromTo = lastLine.split("-".toRegex())
                    tempR.comesFrom = fromTo[1].toInt() - 1
                    tempR.goesTo = fromTo[2].toInt() - 1
                    tempR.text = line.replace("\\n", System.getProperty("line.separator").toString())
                    tempP.responses.addElement(tempR.copy())
                }else if(line == "W"){
                    tempP.winLoss = 'W'
                }else if(line == "L"){
                    tempP.winLoss = 'L'
                }

                lastLine = line
            }

            // The last prompt in the story doesn't have a chance to be pushed to the prompt list inside the loop so it's added here
            prompts.addElement(tempP.copy(tempP.text, tempP.responses.clone() as Vector<Response>, tempP.winLoss))
            tempP.responses.clear()
            tempP.winLoss = ' '

        }catch(e:Exception){
            e.printStackTrace()
        }
    }
}




// Holds the list of stories in the given file and initializes them
class Library(myContext: Context, fileList: String){
    var library: Vector<Story> = Vector()

    // Holds the story that is currently under construction
    private var temp: Story = Story()

    // Initialize each story from the given file and add it to the library
    init{
        try {
            val lines: List<String> = myContext.assets.open(fileList).bufferedReader().readLines()
            lines.forEach{
                //temp.story.clear()
                temp = Story(myContext, it)
                library.addElement(temp)
            }
        }catch(e:Exception){
            e.printStackTrace()
        }
    }
}
