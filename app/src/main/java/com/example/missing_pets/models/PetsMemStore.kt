package com.example.missing_pets.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class PetsMemStore : PetsStore, AnkoLogger {

    val petss = ArrayList<PetsModel>()

    override fun findAll(): List<PetsModel> {
        return petss
    }

    override fun create(pets: PetsModel) {
        pets.id = getId()
        petss.add(pets)
        logAll()
    }

    override fun update(pets: PetsModel) {
        var foundPets: PetsModel? = petss.find { p -> p.id == pets.id }
        if (foundPets != null) {
            foundPets.title = pets.title
            foundPets.description = pets.description
            foundPets.image = pets.image
            foundPets.lat = pets.lat
            foundPets.lng = pets.lng
            foundPets.zoom = pets.zoom
            logAll();
        }
    }

    fun logAll() {
        petss.forEach { info("${it}") }
    }
}

