package com.example.missing_pets.main

import android.app.Application
import com.example.missing_pets.models.PetsStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class MainApp : Application(), AnkoLogger {

    lateinit var petss: PetsStore

    override fun onCreate() {
        super.onCreate()
        petss = PetsJSONStore(applicationContext)
        info("Pets started")
    }
}
