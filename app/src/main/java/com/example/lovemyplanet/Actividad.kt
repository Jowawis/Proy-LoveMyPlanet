package com.example.lovemyplanet

import java.io.Serializable

data class Actividad(val nombre: String = "", val descripcion: String  = "", val puntos:Int  = 0, val urlImage: String = "") : Serializable
