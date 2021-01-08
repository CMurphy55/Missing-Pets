package my.android.project.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class PetMemStore : PetStore, AnkoLogger {

  val pets = ArrayList<PetModel>()

  override fun findAll(): List<PetModel> {
    return pets
  }

  override fun create(pet: PetModel) {
    pet.id = getId()
    pets.add(pet)
    logAll()
  }

  override fun update(pet: PetModel) {
    var foundPet: PetModel? = pets.find { p -> p.id == pet.id }
    if (foundPet != null) {
      foundPet.title = pet.title
      foundPet.description = pet.description
      foundPet.image = pet.image
      foundPet.lat = pet.lat
      foundPet.lng = pet.lng
      foundPet.zoom = pet.zoom
      logAll();
    }
  }

  fun logAll() {
    pets.forEach { info("${it}") }
  }
}

