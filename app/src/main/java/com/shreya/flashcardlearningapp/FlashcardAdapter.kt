package com.shreya.flashcardlearningapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FlashcardAdapter(
    private val flashcards: MutableList<Flashcard>,
    private val onDeleteClickListener: (Int) -> Unit
) : RecyclerView.Adapter<FlashcardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flashcard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flashcard = flashcards[position]
        holder.questionTextView.text = flashcard.question
        holder.answerTextView.text = flashcard.answer

        // Set click listener to flip the card
        holder.cardView.setOnClickListener {
            if (holder.answerTextView.visibility == View.VISIBLE) {
                holder.answerTextView.visibility = View.GONE
                holder.questionTextView.visibility = View.VISIBLE
            } else {
                holder.questionTextView.visibility = View.GONE
                holder.answerTextView.visibility = View.VISIBLE
            }
        }

        // Set delete button listener
        holder.deleteButton.setOnClickListener {
            onDeleteClickListener(position)  // Notify the activity to delete the flashcard
        }
    }

    override fun getItemCount() = flashcards.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardView)
        val questionTextView: TextView = view.findViewById(R.id.questionTextView)
        val answerTextView: TextView = view.findViewById(R.id.answerTextView)
        val deleteButton: ImageView = view.findViewById(R.id.deleteButton)
    }

    // Add Swipe to Delete functionality
    fun enableSwipeToDelete(recyclerView: RecyclerView) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                // Remove the item from the list when swiped
                flashcards.removeAt(position)
                notifyItemRemoved(position)  // Notify the adapter about the removed item
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
