package com.example.fetchdatafromwebtutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ModifierPlaqueUtilisateur : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modifier_plaque_utilisateur)

        spinner = findViewById(R.id.spinner)
        fetchData()
    }

    private fun fetchData() {
        Thread {
            val url = URL("https://89664529-4f7d-4aa4-bdd7-1a48c9bb8a88.mock.pstmn.io/utilisateurs")
            val connection = url.openConnection() as HttpsURLConnection

            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Array<Request>::class.java).toList()

                inputStreamReader.close()
                inputSystem.close()
                runOnUiThread {
                    updatePlaque(request)
                }
                println("Connection Success")
            } else {
                println("Failed Connection")
            }
        }.start()
    }

    private fun fetchData2() {
        Thread {
            val url = URL("https://89664529-4f7d-4aa4-bdd7-1a48c9bb8a88.mock.pstmn.io/utilisateurs")
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "PUT"
            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request2::class.java)

                inputStreamReader.close()
                inputSystem.close()
                runOnUiThread {
                    // Effectuer ici les mises à jour de l'interface utilisateur si nécessaire
                }
                val gson = Gson()
                val respJson = gson.toJson(request)

                val intent = Intent(this, Reussie3::class.java)
                intent.putExtra("reponse", respJson)
                startActivity(intent)
            } else {
                println("Connection failed")
            }
        }.start()
    }

    private fun updatePlaque(userList: List<Request>) {
        val items = mutableListOf<String>()
        var count=0


        for (user in userList) {
            var nbplaque=0
            val plaques = listOf(user.plaque1, user.plaque2, user.plaque3, user.plaque4, user.plaque5)
            for (plaque in plaques) {
                if (plaque != null) {
                    nbplaque++ // Incrémenter le compteur pour chaque plaque non nulle
                }
            }


            for (plaque in plaques) {
                if (plaque!=null && count <= nbplaque) {
                    items.add(plaque)
                    count++
                }
            }

            if (count >= 5) {
                break
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        val selectedItem = parent.getItemAtPosition(position).toString()

        val myTextView: TextView = findViewById(R.id.modif)
        myTextView.text = selectedItem

        val buttonModifier = findViewById<Button>(R.id.modif3)
        buttonModifier.setOnClickListener {
            fetchData2()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Ne rien faire ici, car cette fonction n'est pas utilisée
    }

}
