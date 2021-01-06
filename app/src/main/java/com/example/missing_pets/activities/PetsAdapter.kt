package com.example.missing_pets.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.missing_pets.R


interface PetsListener {
    fun onPetsClick(pets: PetsModel)
}

class PetsAdapter constructor(private var petss: List<PetsModel>,
                              private val listener: PetsListener) : RecyclerView.Adapter<PetsAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_pets, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val pets = petss[holder.adapterPosition]
        holder.bind(pets, listener)
    }

    override fun getItemCount(): Int = petss.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(pets: PetsModel, listener: PetsListener) {
            itemView.petsTitle.text = pets.title
            itemView.description.text = pets.description
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, pets.image))
            itemView.setOnClickListener { listener.onPetsClick(pets) }
        }
    }
}
