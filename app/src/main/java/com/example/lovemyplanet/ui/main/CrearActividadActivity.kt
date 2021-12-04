package com.example.lovemyplanet.ui.main

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.lovemyplanet.Actividad
import com.example.lovemyplanet.R
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*

class CrearActividadActivity : AppCompatActivity() {

    private var returnValue = ArrayList<String>()
    lateinit var progressDialog: ProgressDialog
    lateinit var options : Options
    lateinit var image: CircleImageView
    lateinit var imgFile: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_actividad)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        var txtNombre = findViewById<EditText>(R.id.txtNombre)
        var txtDescrip = findViewById<EditText>(R.id.txtDescrip)
        var txtPuntos =  findViewById<EditText>(R.id.txtPuntos)
        var btnImage =  findViewById<FloatingActionButton>(R.id.btnImage)
        image = findViewById(R.id.circleImgPhoto)
        btnRegistrar.setOnClickListener{
            saveImage(txtNombre.text.toString(),txtDescrip.text.toString(),txtPuntos.text.toString())
        }

        options = Options.init()
            .setRequestCode(100) //Request code for activity results
            .setCount(3) //Number of images to restict selection count
            .setFrontfacing(false) //Front Facing camera on start
            .setPreSelectedUrls(returnValue) //Pre selected Image Urls
            .setSpanCount(4) //Span count for gallery min 1 & max 5
            .setExcludeVideos(false) //Option to exclude videos
            .setVideoDurationLimitinSeconds(0) //Duration for video recording
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT) //Orientaion
            .setPath("/pix/images")
        btnImage.setOnClickListener {
            startPix()
        }

    }

    private fun registrarActividad(nombre: String, descripcion: String, puntos: String, urlImage: String) {
        val actividad = Actividad(nombre,descripcion,puntos.toInt(),urlImage)
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection("actividades")
            .document()
            .set(actividad, SetOptions.merge())
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Actividad Creada", Toast.LENGTH_SHORT).show()
                finish()
            }

    }

    private fun saveImage(nombre: String, descripcion: String, puntos: String) {

        if (!this::imgFile.isInitialized){
            Toast.makeText(this,"Ingrese una imagen", Toast.LENGTH_SHORT).show()
            return
        }


        if (nombre.isEmpty()){
            Toast.makeText(this,"Ingrese el nombre de la actividad", Toast.LENGTH_SHORT).show()
            return
        }

        if (descripcion.isEmpty()){
            Toast.makeText(this,"Ingrese la descripción de la actividad", Toast.LENGTH_SHORT).show()
            return
        }

        if (puntos.isEmpty()){
            Toast.makeText(this,"Ingrese los puntos de la actividad", Toast.LENGTH_SHORT).show()
            return
        }


        val storageReference = FirebaseStorage.getInstance().reference.child(Date().toString() + ".jpg")
        val task: UploadTask = storageReference.putFile(imgFile)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Registrando...")
        progressDialog.show()
        task.addOnCompleteListener{
            if (task.isSuccessful) {
                storageReference.getDownloadUrl().addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                    val url = uri.toString()
                    Log.d("TAG1", url)

                    registrarActividad(nombre,descripcion,puntos,url)

                })
            } else {
                Toast.makeText(
                    this,
                    "Error al guardar imagén",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun startPix() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            // Launches the image selection of phone storage using the constant code.
            startActivityForResult(galleryIntent, 2)
        } else {
            /*Requests permissions to be granted to this application. These permissions
             must be requested in your manifest, they should not be granted to your app,
             and they should have protection level*/
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                2
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == 2
            && data!!.data != null
        ) {


            imgFile = data.data!!
            try {

                Glide
                    .with(applicationContext)
                    .load(imgFile)
                    .centerCrop()
                    .into(image)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

