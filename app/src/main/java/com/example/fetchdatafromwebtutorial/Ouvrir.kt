package com.example.fetchdatafromwebtutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

class Ouvrir : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ouvrir)

        val host = "172.16.30.2"
        val port = 8000

        Thread {
            val socket = Socket(host, port)
            try {
                val message = "Bonjour, serveur!"

                val outputStream: OutputStream = socket.getOutputStream()
                outputStream.write(message.toByteArray())

                val inputStream = socket.getInputStream()
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = reader.readLine()

                runOnUiThread {
                    println("Réponse du serveur: $response")
                    // Faites ici tout traitement supplémentaire sur l'interface utilisateur si nécessaire
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("reussie")
            } finally {
                socket.close()
            }
        }.start()
    }
}
