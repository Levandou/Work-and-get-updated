package com.example.tinkoffpractice.map

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.presentation.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var cancellationTokenSource = CancellationTokenSource()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel by viewModel<MapViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        val locationBtn = view.findViewById<FloatingActionButton>(R.id.btn_location)
        val plus = view.findViewById<FloatingActionButton>(R.id.btn_plus)
        val minus = view.findViewById<FloatingActionButton>(R.id.btn_minus)
        locationBtn.setOnClickListener {
            fetchLocation()
        }

        plus.setOnClickListener {
            mMap?.let {
                mMap?.animateCamera(CameraUpdateFactory.zoomTo(it.cameraPosition.zoom + 1))
            }
        }

        minus.setOnClickListener {
            mMap?.let {
                mMap?.animateCamera(CameraUpdateFactory.zoomTo(it.cameraPosition.zoom - 1))
            }
        }

        viewModel.pointsList.observe(viewLifecycleOwner) {
            it.forEach { point ->
                try {
                    mMap?.addMarker(MarkerOptions().position(LatLng(point.lat?.toDouble() ?: 0.0, point.lng?.toDouble() ?: 0.0)).title(point.title))
                } catch (e: Exception) {
                    Log.d("dasdasdsa", e.toString())
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.let { map ->
            val sydney = LatLng(-34.0, 151.0)
            map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
        mMap?.setOnMarkerClickListener { marker ->
            val point = viewModel.pointsList.value?.firstOrNull() {
                it.lng == marker.position.longitude.toString() && it.lat == marker.position.latitude.toString()
            }
            Log.d("qwertyui", point.toString())
            point?.let {
                PointDialogFragment(it).show(requireActivity().supportFragmentManager, "tag")
            }

            true
        }
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        val currentLocationTask = fusedLocationProviderClient.getCurrentLocation(
            PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )

        currentLocationTask.addOnCompleteListener { task: Task<Location> ->
            if (task.isSuccessful) {
                val result: Location = task.result
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(result.latitude, result.longitude), 17f))
            } else {
                val exception = task.exception
            }
        }
    }
}