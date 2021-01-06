package com.example.missing_pets.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger

import java.util.*

val JSON_FILE = "petss.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<PetsModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class PetsJSONStore : PetsStore, AnkoLogger {

    val context: Context
    var petss = mutableListOf<PetsModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PetsModel> {
        return petss
    }

    override fun create(pets: PetsModel) {
        pets.id = generateRandomId()
        petss.add(pets)
        serialize()
    }

    override fun update(pets: PetsModel) {
        val petssList = findAll() as ArrayList<PetsModel>
        var foundPets: PetsModel? = petssList.find { p -> p.id == pets.id }
        if (foundPets != null) {
            foundPets.title = pets.title
            foundPets.description = pets.description
            foundPets.image = pets.image
            foundPets.lat = pets.lat
            foundPets.lng = pets.lng
            foundPets.zoom = pets.zoom
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(petss, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        petss = Gson().fromJson(jsonString, listType)
    }
}
