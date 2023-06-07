package com.example.fetchdatafromwebtutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ModifierPlaqueAdministrateur : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var spinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modifier_administrateur)
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
        }.start()
    }
    private fun updatePlaque(userList: List<Request>) {
        val recue=intent.getStringExtra("reponse")
        val items = mutableListOf<String>()

        for (user in userList) {
            if(user.username==recue) {
                items.add("ID: ${user.id}")
                items.add("Username: ${user.username}")
                items.add("Password: ${user.mdp}")
                items.add("Status: ${user.status}")
                items.add("Plaque 1: ${user.plaque1}")
                items.add("Plaque 2: ${user.plaque2}")
                items.add("Plaque 3: ${user.plaque3}")
                items.add("Plaque 4: ${user.plaque4}")
                items.add("Plaque 5: ${user.plaque5}")
                break

            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = this
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position)?.toString()

        val myTextView: TextView = findViewById(R.id.myTextView)
        myTextView.text = selectedItem

        val buttonModifier = findViewById<Button>(R.id.modifier)
        buttonModifier.setOnClickListener {
            fetchData2()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing here, as this function is not used
    }

}