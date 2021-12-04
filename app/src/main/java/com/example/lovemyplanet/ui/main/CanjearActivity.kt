package com.example.lovemyplanet.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.lovemyplanet.R
import com.example.lovemyplanet.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore

class CanjearActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canjear)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tvPuntos = findViewById<TextView>(R.id.tvPuntos)

        val fireStore = FirebaseFirestore.getInstance()
        val auth =  FirebaseAuth.getInstance();
        fireStore.collection("usuarios")
            .document(auth.currentUser!!.uid ).addSnapshotListener(
                EventListener<DocumentSnapshot?> { userEvent, error ->
                    val usuario = userEvent!!.toObject(Usuario::class.java)!!
                    tvPuntos.text = "Puntos: "+usuario.puntos.toString()
          })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}