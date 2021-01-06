package com.example.missing_pets.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.missing_pets.R
import com.example.missing_pets.main.MainApp

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast


class PetsActivity : AppCompatActivity(), AnkoLogger {

    var pets = PetsModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pets)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Pets Activity started..")

        app = application as MainApp
        var edit = false

        if (intent.hasExtra("pets_edit")) {
            edit = true
            pets = intent.extras?.getParcelable<PetsModel>("pets_edit")!!
            petsTitle.setText(pets.title)
            description.setText(pets.description)
            petsImage.setImageBitmap(readImageFromPath(this, pets.image))
            if (pets.image != null) {
                chooseImage.setText(R.string.change_pets_image)
            }
            btnAdd.setText(R.string.save_pets)
        }

        btnAdd.setOnClickListener() {
            pets.title = petsTitle.text.toString()
            pets.description = description.text.toString()
            if (pets.title.isEmpty()) {
                toast(R.string.enter_pets_title)
            } else {
                if (edit) {
                    app.petss.update(pets.copy())
                } else {
                    app.petss.create(pets.copy())
                }
            }
            info("add Button Pressed: $petsTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        petsLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (pets.zoom != 0f) {
                location.lat = pets.lat
                location.lng = pets.lng
                location.zoom = pets.zoom
            }
            startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pets, menu)
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
                    pets.image = data.getData().toString()
                    petsImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_pets_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    pets.lat = location.lat
                    pets.lng = location.lng
                    pets.zoom = location.zoom
                }
            }
        }
    }
}


