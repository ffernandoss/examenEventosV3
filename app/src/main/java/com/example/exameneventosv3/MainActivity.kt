package com.example.exameneventosv3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {

    private lateinit var jsonTextView: TextView
    private lateinit var iconImageView: ImageView
    private lateinit var jsonObject: JSONObject
    private lateinit var farmaciasLayout: LinearLayout
    private var isContentVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia a los elementos de la interfaz
        jsonTextView = findViewById(R.id.jsonTextView)
        iconImageView = findViewById(R.id.iconImageView)
        farmaciasLayout = findViewById(R.id.farmaciasLayout)  // El contenedor para las farmacias
        val buttonTitle: Button = findViewById(R.id.buttonTitle)
        val buttonCoordinates: Button = findViewById(R.id.buttonCoordinates)
        val buttonLink: Button = findViewById(R.id.buttonLink)
        val buttonDescription: Button = findViewById(R.id.buttonDescription)
        val buttonTelefono: Button = findViewById(R.id.buttonTelefono)
        val buttonFarmacias: Button = findViewById(R.id.buttonFarmacias)

        // Lee el archivo JSON desde assets
        val json = assets.open("data.json").bufferedReader().use(BufferedReader::readText)
        jsonObject = JSONObject(json)

        // Configuración de los botones
        buttonTitle.setOnClickListener { toggleContent { showSpecificField("title") } }
        buttonCoordinates.setOnClickListener { toggleContent { showSpecificField("coordinates") } }
        buttonLink.setOnClickListener { toggleContent { showSpecificField("link") } }
        buttonDescription.setOnClickListener { toggleContent { showSpecificField("description") } }
        buttonTelefono.setOnClickListener { toggleContent { showTelefonos() } }
        buttonFarmacias.setOnClickListener { toggleContent { showFarmacias() } }
    }

    private fun toggleContent(action: () -> Unit) {
        if (isContentVisible) {
            jsonTextView.text = ""
            farmaciasLayout.removeAllViews()
        } else {
            action()
        }
        isContentVisible = !isContentVisible
    }

    private fun showFarmacias() {
        val featuresArray = jsonObject.getJSONArray("features")
        farmaciasLayout.removeAllViews()  // Limpiar la vista antes de agregar nuevas farmacias

        // Recorrer las farmacias
        for (i in 0 until featuresArray.length()) {
            val feature = featuresArray.getJSONObject(i)
            val name = feature.getJSONObject("properties").getString("title")
            val description = feature.getJSONObject("properties").getString("description")
            val coordinates = feature.getJSONObject("geometry").getJSONArray("coordinates")
            val telefono = Regex("Teléfono: ([0-9]+)").find(description)?.groupValues?.get(1)
                ?: "No encontrado"

            // Crear un LinearLayout para cada farmacia
            val farmaciaLayout = LinearLayout(this)
            farmaciaLayout.orientation = LinearLayout.HORIZONTAL
            farmaciaLayout.setPadding(10, 10, 10, 10)

            // Crear un TextView para mostrar la información de la farmacia
            val farmaciaInfo = TextView(this)
            farmaciaInfo.text = "Farmacia ${i + 1}:\nNombre: $name\nTeléfono: $telefono\n"

            // Crear un ImageView para mostrar la imagen de la farmacia
            val farmaciaImage = ImageView(this)
            Glide.with(this)
                .load(R.drawable.medicines_icon)  // Usar el ícono de la imagen cargada (sin extensión .png)
                .into(farmaciaImage)  // Mostrar la imagen en el ImageView
            farmaciaImage.layoutParams = LinearLayout.LayoutParams(100, 100)  // Ajustar tamaño de la imagen

            // Agregar el TextView y el ImageView al LinearLayout de la farmacia
            farmaciaLayout.addView(farmaciaImage)
            farmaciaLayout.addView(farmaciaInfo)

            // Agregar un clic a cada farmacia para abrir el detalle
            farmaciaLayout.setOnClickListener {
                val coordenadas = coordinates.join(", ")
                val intent = Intent(this, FarmaciaDetailActivity::class.java)
                intent.putExtra("coordenadas", coordenadas)
                intent.putExtra("description", description)
                startActivity(intent)
            }

            // Agregar el LinearLayout de la farmacia al contenedor principal
            farmaciasLayout.addView(farmaciaLayout)
        }
    }

    private fun showSpecificField(field: String) {
        val featuresArray = jsonObject.getJSONArray("features")
        val result = StringBuilder()

        // Recorremos las características y mostramos los campos específicos
        for (i in 0 until featuresArray.length()) {
            val feature = featuresArray.getJSONObject(i)
            when (field) {
                "title" -> {
                    val title = feature.getJSONObject("properties").getString("title")
                    result.append("Title ${i + 1}: $title\n")
                }

                "coordinates" -> {
                    val coordinates = feature.getJSONObject("geometry").getJSONArray("coordinates")
                    result.append("Coordinates ${i + 1}: ${coordinates.join(", ")}\n")
                }

                "link" -> {
                    val link = feature.getJSONObject("properties").getString("link")
                    result.append("Link ${i + 1}: $link\n")
                }

                "description" -> {
                    val description = feature.getJSONObject("properties").getString("description")
                    result.append("Description ${i + 1}: $description\n")
                }
            }
        }
        jsonTextView.text = result.toString()
    }

    private fun showTelefonos() {
        val featuresArray = jsonObject.getJSONArray("features")
        val telefonos = StringBuilder()

        // Recorremos las farmacias y extraemos los teléfonos
        for (i in 0 until featuresArray.length()) {
            val feature = featuresArray.getJSONObject(i)
            val description = feature.getJSONObject("properties").getString("description")
            val telefono = Regex("Teléfono: ([0-9]+)").find(description)?.groupValues?.get(1)
                ?: "No encontrado"
            telefonos.append("Teléfono ${i + 1}: $telefono\n")
        }
        jsonTextView.text = telefonos.toString()
    }
}
