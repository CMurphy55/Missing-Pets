package my.android.project.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import my.android.project.models.PetJSONStore
import my.android.project.models.PetStore


class MainApp : Application(), AnkoLogger {

  lateinit var pets: PetStore

  override fun onCreate() {
    super.onCreate()
    pets = PetJSONStore(applicationContext)
    info("Pet started")
  }
}

