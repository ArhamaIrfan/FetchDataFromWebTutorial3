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

class Administrateur_acces : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.administrateur_acces)

        val recue = intent.getStringExtra("reponse")
        val recue2 = recue.toString()
        var message = ""




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
                println("Connecction Success")
            } else {
                println("Erreur")
            }
        }.start()
    }

    private fun updatePlaque(userList: List<Request>) {
        val spinner: Spinner = findViewById(R.id.spinner)
        val items = mutableListOf<String>()
        var count = 0
        var nbUsers = 0

        for (user in userList) {
            if (nbUsers < 5) {
                val utilisateur = user.username
                items.add(utilisateur)
                nbUsers++
            } else {
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


        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            val intent = Intent(this, ModifierPlaqueAdministrateur::class.java)
            intent.putExtra("reponse", selectedItem)
            println(selectedItem)
            startActivity(intent)
        }

        val button6 = findViewById<Button>(R.id.button6)
        button6.setOnClickListener {
            val intent = Intent(this, SupprimerPlaqueAdministrateur::class.java)
            intent.putExtra("reponse", selectedItem)
            println(selectedItem)
            startActivity(intent)
        }
        val button7 = findViewById<Button>(R.id.button7)
        button7.setOnClickListener {
            val intent = Intent(this, AjouterUtilisateur::class.java)
            startActivity(intent)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Ne rien faire ici, cette fonction n'est pas utilisée
    }
}
