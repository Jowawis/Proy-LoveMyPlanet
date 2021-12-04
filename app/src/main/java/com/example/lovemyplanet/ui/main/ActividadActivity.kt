package com.example.lovemyplanet.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lovemyplanet.Actividad
import com.example.lovemyplanet.R
import com.example.lovemyplanet.Usuario
import com.example.lovemyplanet.adapters.AdapterActividad
import com.example.lovemyplanet.adapters.AdapterRecycler
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query

class ActividadActivity : AppCompatActivity(), AdapterActividad.OnActividadClickListener {

    lateinit var usuario: Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rvVoluntarios = findViewById<RecyclerView>(R.id.rvActividad)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        rvVoluntarios.layoutManager = LinearLayoutManager(this)

        val query: Query = FirebaseFirestore.getInstance().collection("actividades");
        val options: FirestoreRecyclerOptions<Actividad> = FirestoreRecyclerOptions.Builder<Actividad>()
            .setQuery(query, Actividad::class.java)
            .build()
        val adapter = AdapterActividad(options,this)
        rvVoluntarios.adapter = adapter
        adapter.startListening()
        rvVoluntarios.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val fireStore = FirebaseFirestore.getInstance()
        val auth =  FirebaseAuth.getInstance();

         fireStore.collection("usuarios")
            .document(auth.currentUser!!.uid ).addSnapshotListener(
                EventListener<DocumentSnapshot?> { userEvent, error ->
                 usuario = userEvent!!.toObject(Usuario::class.java)!!


         })

        fab.setOnClickListener{
            startActivity(Intent(this, CrearActividadActivity::class.java))

        }


    }

    override fun onActividadClick(actividad: Actividad) {

        val bottomSheetActividad = BottomSheetActividad(actividad,usuario)
        bottomSheetActividad.show(supportFragmentManager, bottomSheetActividad.getTag())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


}