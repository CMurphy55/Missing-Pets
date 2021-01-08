package my.android.project.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import my.android.project.helpers.exists

import my.android.project.helpers.read
import my.android.project.helpers.write
import java.util.*

val JSON_FILE = "pets.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<PetModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class PetJSONStore : PetStore, AnkoLogger {

  val context: Context
  var pets = mutableListOf<PetModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(
        context,
        JSON_FILE
      )
    ) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<PetModel> {
    return pets
  }

  override fun create(pet: PetModel) {
    pet.id = generateRandomId()
    pets.add(pet)
    serialize()
  }

  override fun update(pet: PetModel) {
    val petsList = findAll() as ArrayList<PetModel>
    var foundPet: PetModel? = petsList.find { p -> p.id == pet.id }
    if (foundPet != null) {
      foundPet.title = pet.title
      foundPet.description = pet.description
      foundPet.image = pet.image
      foundPet.lat = pet.lat
      foundPet.lng = pet.lng
      foundPet.zoom = pet.zoom
    }
    serialize()
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(pets,
      listType
    )
    write(
      context,
      JSON_FILE,
      jsonString
    )
  }

  private fun deserialize() {
    val jsonString = read(
      context,
      JSON_FILE
    )
    pets = Gson().fromJson(jsonString, listType)
  }
}


