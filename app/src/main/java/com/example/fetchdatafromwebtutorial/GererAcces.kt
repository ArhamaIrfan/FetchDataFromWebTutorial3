package com.example.fetchdatafromwebtutorial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GererAcces : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.administrateur_acces)

        val modifier = findViewById<Button>(R.id.button2)
        modifier.setOnClickListener {
            val intent = Intent(applicationContext, ModifierPlaqueAdministrateur::class.java)
            startActivity(intent)
        }
        val supprimer = findViewById<Button>(R.id.button6)
        supprimer.setOnClickListener {
            val intent = Intent(applicationContext, SupprimerPlaqueAdministrateur::class.java)
            startActivity(intent)
        }
    }
}