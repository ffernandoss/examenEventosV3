package com.example.exameneventosv3

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FarmaciaDetailActivity : AppCompatActivity() {

    private lateinit var coordenadasTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var backgroundImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmacia_detail)

        // Referencia a los elementos de la interfaz
        coordenadasTextView = findViewById(R.id.textViewCoordenadas)
        descriptionTextView = findViewById(R.id.textViewDescription)
        backgroundImageView = findViewById(R.id.backgroundImage)

        // Establecer la imagen de fondo
        backgroundImageView.setImageResource(R.drawable.mapa_zaragoza)

        // Obtener los datos enviados desde MainActivity
        val coordenadas = intent.getStringExtra("coordenadas") ?: "No disponible"
        val description = intent.getStringExtra("description") ?: "No disponible"

        // Mostrar los datos en los TextViews
        coordenadasTextView.text = "Coordenadas: $coordenadas"
        descriptionTextView.text = "Descripción: $description"
    }
}
