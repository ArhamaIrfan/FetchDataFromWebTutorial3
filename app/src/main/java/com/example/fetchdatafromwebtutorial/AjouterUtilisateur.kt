package com.example.fetchdatafromwebtutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fetchdatafromwebtutorial.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class AjouterUtilisateur : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var status: EditText
    private lateinit var plaque1: EditText
    private lateinit var plaque2: EditText
    private lateinit var plaque3: EditText
    private lateinit var plaque4: EditText
    private lateinit var plaque5: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajouter_utilisateur)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        status = findViewById(R.id.status)
        plaque1 = findViewById(R.id.plaque1)
        plaque2 = findViewById(R.id.plaque2)
        plaque3 = findViewById(R.id.plaque3)
        plaque4 = findViewById(R.id.plaque4)
        plaque5 = findViewById(R.id.plaque5)

        val submitButton = findViewById<Button>(R.id.submit)
        submitButton.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        val identifiant = username.text.toString().trim()
        val mdp = password.text.toString().trim()
        val stat = status.text.toString().trim()
        val pl1 = plaque1.text.toString().trim()
        val pl2 = plaque2.text.toString().trim()
        val pl3 = plaque3.text.toString().trim()
        val pl4 = plaque4.text.toString().trim()
        val pl5 = plaque5.text.toString().trim()

        if (identifiant.isEmpty() || mdp.isEmpty() || stat.isEmpty() || pl1.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            // Perform form submission logic here
            fetchData2()
        }
    }

    private fun fetchData2() {
        Thread {
            val url = URL("https://89664529-4f7d-4aa4-bdd7-1a48c9bb8a88.mock.pstmn.io/utilisateurs")
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "POST"

            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val resp = Gson().fromJson(inputStreamReader, Request2::class.java)

                inputStreamReader.close()
                inputSystem.close()

                runOnUiThread {
                    val gson = Gson()
                    val respJson = gson.toJson(resp)

                    val intent = Intent(this, Reussie3::class.java)
                    intent.putExtra("reponse", respJson)
                    startActivity(intent)
                }
            } else {
                println("Connection failed")
            }
        }.start()
    }
}
