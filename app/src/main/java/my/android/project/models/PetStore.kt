package my.android.project.models

interface PetStore {
  fun findAll(): List<PetModel>
  fun create(pet: PetModel)
  fun update(pet: PetModel)
}


