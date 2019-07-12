package com.example.basis.ui.swipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basis.R
import com.example.basis.data.model.Card
import javax.inject.Inject

/*
Adapter class for CardStackView
 */
class SwipeAdapter @Inject constructor() : RecyclerView.Adapter<SwipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.card_layout, parent, false))
    }

    private lateinit var cards: List<Card>

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = cards[position].id
        holder.text.text = cards[position].name
    }

    override fun getItemCount() = cards.size

    fun setCards(swipeCards: List<Card>) {
        cards = swipeCards
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.card_id)
        val text: TextView = view.findViewById(R.id.card_text)
    }
}