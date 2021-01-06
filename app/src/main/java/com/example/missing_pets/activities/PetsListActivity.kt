package com.example.missing_pets.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.missing_pets.R
import com.example.missing_pets.main.MainApp

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class PetsListActivity : AppCompatActivity(), PetsListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pets_list)
        app = application as MainApp
        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = PetsAdapter(app.pets.findAll(), this)
        loadPetss()
    }

    private fun loadPetss() {
        showPetss( app.pets.findAll())
    }

    fun showPetss (petss: List<PetsModel>) {
        recyclerView.adapter = PetsAdapter(petss, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<PetsActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPetsClick(pets: PetsModel) {
        startActivityForResult(intentFor<PetsActivity>().putExtra("pets_edit", pets), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadPetss()
        super.onActivityResult(requestCode, resultCode, data)
    }
}
