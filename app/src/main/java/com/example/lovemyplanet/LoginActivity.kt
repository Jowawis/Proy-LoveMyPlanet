package com.example.lovemyplanet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val txtEmail : EditText = findViewById(R.id.txtEmail)
        val txtPassword : EditText = findViewById(R.id.txtPass)
        val btnLogin : Button = findViewById(R.id.btn_login)

        txtEmail.setText("16100953@ue.edu.pe")
        txtPassword.setText("123456")

        btnLogin.setOnClickListener{
            login(txtEmail.text.toString().trim(),txtPassword.text.toString()   )
        }

    }

    fun login(correo: String,clave: String){
        val firebaseAuth = FirebaseAuth.getInstance()

        if(correo.isEmpty()){
            Toast.makeText(applicationContext,"Ingrese su correo",Toast.LENGTH_SHORT).show()
            return
        }

        if(clave.isEmpty()){
            Toast.makeText(applicationContext,"Ingrese su clave",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(correo ,clave)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Inicio Satisfactorio", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, TappedActivity::class.java))
                } else   {
                    Toast.makeText(this, "Credenciales incorrectas ", Toast.LENGTH_LONG).show()
                }
            }
       }

}