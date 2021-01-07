package my.android.project.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pet_list.*

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.placemark.R
import my.android.project.main.MainApp
import my.android.project.models.PetModel


class PetListActivity : AppCompatActivity(),
  PetListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pet_list)
    app = application as MainApp
    toolbar.title = title
    setSupportActionBar(toolbar)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter =
      PetAdapter(app.pets.findAll(), this)
    loadPets()
  }

  private fun loadPets() {
    showPets( app.pets.findAll())
  }

  fun showPets (pets: List<PetModel>) {
    recyclerView.adapter = PetAdapter(pets, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_add -> startActivityForResult<PetActivity>(0)
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onPetClick(pet: PetModel) {
    startActivityForResult(intentFor<PetActivity>().putExtra("pet_edit", pet), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadPets()
    super.onActivityResult(requestCode, resultCode, data)
  }
}

