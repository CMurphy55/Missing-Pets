package my.android.project.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_pet.view.*

import my.android.project.helpers.readImageFromPath
import my.android.project.models.PetModel
import org.wit.placemark.R

interface PetListener {
  fun onPetClick(pet: PetModel)
}

class PetAdapter constructor(private var pets: List<PetModel>,
                             private val listener: PetListener
) : RecyclerView.Adapter<PetAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(
      LayoutInflater.from(
        parent.context
      ).inflate(R.layout.card_pet, parent, false)
    )
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val pet = pets[holder.adapterPosition]
    holder.bind(pet, listener)
  }

  override fun getItemCount(): Int = pets.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(pet: PetModel, listener: PetListener) {
      itemView.petTitle.text = pet.title
      itemView.description.text = pet.description
      itemView.imageIcon.setImageBitmap(
        readImageFromPath(
          itemView.context,
          pet.image
        )
      )
      itemView.setOnClickListener { listener.onPetClick(pet) }
    }
  }
}

