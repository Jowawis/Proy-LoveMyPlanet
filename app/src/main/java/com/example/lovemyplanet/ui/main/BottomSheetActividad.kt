package com.example.lovemyplanet.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.lovemyplanet.Actividad
import com.example.lovemyplanet.R
import com.example.lovemyplanet.Usuario
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import java.lang.String
import java.util.HashMap

class BottomSheetActividad(val actividad: Actividad, val usuario: Usuario): BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.bottom_sheet_actividad, container, false)
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        val btnCancel : Button = view.findViewById(R.id.btnCancel)
        val btnAceptar: Button  = view.findViewById(R.id.btnSave)
        val tvDescrip: TextView = view.findViewById(R.id.tvDescrip)
        tvDescrip.setText("Desea aceptar la actividad '"+actividad.nombre+"' ?")
        btnAceptar.setOnClickListener {
            actualizarPuntos()
        }
        btnCancel.setOnClickListener { dismiss() }
        return view.rootView
    }

    private fun actualizarPuntos() {
        val fireStore = FirebaseFirestore.getInstance()
        val auth =  FirebaseAuth.getInstance();

        val nuevosPuntos = usuario!!.puntos + actividad.puntos
        usuario.puntos =  nuevosPuntos
        fireStore.collection("usuarios")
            .document(auth.currentUser!!.uid )
            .set(usuario, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(requireActivity(),"Puntos añadidos", Toast.LENGTH_SHORT).show()
                dismiss()
            }

    }

}

