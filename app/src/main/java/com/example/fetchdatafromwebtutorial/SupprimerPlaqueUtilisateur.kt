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

class SupprimerPlaqueUtilisateur : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supprimer)

        fetchData().start()
    }

    private fun fetchData(): Thread {
        return Thread {
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
        }
    }

    private fun updatePlaque(userList: List<Request>) {
        val spinner: Spinner = findViewById(R.id.spinner)
        val items = mutableListOf<String>()

        for (user in userList) {
            val plaque1 = user.plaque1
            val plaque2 = user.plaque2
            val plaque3 = user.plaque3
            val plaque4 = user.plaque4
            val plaque5 = user.plaque5

            if (!plaque1.isNullOrEmpty()) {
                items.add(plaque1)
            }
            if (!plaque2.isNullOrEmpty()) {
                items.add(plaque2)
            }
            if (!plaque3.isNullOrEmpty()) {
                items.add(plaque3)
            }
            if (!plaque4.isNullOrEmpty()) {
                items.add(plaque4)
            }
            if (!plaque5.isNullOrEmpty()) {
                items.add(plaque5)
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        val selectedItem = parent.getItemAtPosition(position).toString()

        val myTextView: TextView = findViewById(R.id.myTextView)
        myTextView.text = selectedItem

        val buttonModifier = findViewById<Button>(R.id.modifier)
        buttonModifier.setOnClickListener {
            fetchData2().start()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Do nothing here, as this function is not used
    }

    private fun fetchData2(): Thread {
        return Thread {
            val url = URL("https://89664529-4f7d-4aa4-bdd7-1a48c9bb8a88.mock.pstmn.io/utilisateurs")
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "DELETE"
            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request2::class.java)

                inputStreamReader.close()
                inputSystem.close()
                runOnUiThread {
                    // Perform any UI updates here if needed
                }
                val gson = Gson()
                val respJson = gson.toJson(request)

                val intent = Intent(this, Reussie3::class.java)
                intent.putExtra("reponse", respJson)
                startActivity(intent)
            } else {
                println("Connection failed")
            }
        }
    }
}
