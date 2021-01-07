package my.android.project.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_pet.*

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import my.android.project.helpers.readImage
import my.android.project.helpers.readImageFromPath
import my.android.project.helpers.showImagePicker
import my.android.project.main.MainApp
import my.android.project.models.Location
import my.android.project.models.PetModel
import org.wit.placemark.R


class PetActivity : AppCompatActivity(), AnkoLogger {

  var pet = PetModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pet)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Pet Activity started..")

    app = application as MainApp
    var edit = false

    if (intent.hasExtra("pet_edit")) {
      edit = true
      pet = intent.extras?.getParcelable<PetModel>("pet_edit")!!
      petTitle.setText(pet.title)
      description.setText(pet.description)
      petImage.setImageBitmap(
        readImageFromPath(
          this,
          pet.image
        )
      )
      if (pet.image != null) {
        chooseImage.setText(R.string.change_pet_image)
      }
      btnAdd.setText(R.string.save_pet)
    }

    btnAdd.setOnClickListener() {
      pet.title = petTitle.text.toString()
      pet.description = description.text.toString()
      if (pet.title.isEmpty()) {
        toast(R.string.enter_pet_title)
      } else {
        if (edit) {
          app.pets.update(pet.copy())
        } else {
          app.pets.create(pet.copy())
        }
      }
      info("add Button Pressed: $petTitle")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }

    petLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (pet.zoom != 0f) {
        location.lat = pet.lat
        location.lng = pet.lng
        location.zoom = pet.zoom
      }
      startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_pet, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          pet.image = data.getData().toString()
          petImage.setImageBitmap(
            readImage(
              this,
              resultCode,
              data
            )
          )
          chooseImage.setText(R.string.change_pet_image)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras?.getParcelable<Location>("location")!!
          pet.lat = location.lat
          pet.lng = location.lng
          pet.zoom = location.zoom
        }
      }
    }
  }
}



