package my.android.project.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import my.android.project.models.Location
import org.wit.placemark.R


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {


  private lateinit var map: GoogleMap
  var location = Location()

  override fun onMarkerDragStart(marker: Marker) {
  }

  override fun onMarkerDrag(marker: Marker) {
  }

  override fun onMarkerDragEnd(marker: Marker) {
    location.lat = marker.position.latitude
    location.lng = marker.position.longitude
    location.zoom = map.cameraPosition.zoom
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map)
    location = intent.extras?.getParcelable<Location>("location")!!
    val bit = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    bit.getMapAsync(this)
  }

  override fun onBackPressed() {
    val resultIntent = Intent()
    resultIntent.putExtra("location", location)
    setResult(Activity.RESULT_OK, resultIntent)
    finish()
    super.onBackPressed()
  }

  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap
    map.setOnMarkerDragListener(this)
    map.setOnMarkerClickListener(this)
    val destination = LatLng(location.lat, location.lng)
    val options = MarkerOptions()
      .title("Missing_Pets")
      .snippet("GPS : " + destination.toString())
      .draggable(true)
      .position(destination)
    map.addMarker(options)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, location.zoom))
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    val loc = LatLng(location.lat, location.lng)
    marker.setSnippet("GPS : " + loc.toString())
    return false
  }
}