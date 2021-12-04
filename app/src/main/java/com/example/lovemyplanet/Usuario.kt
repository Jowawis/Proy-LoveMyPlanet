package com.example.lovemyplanet

import java.io.Serializable

data class Usuario(var id: String = "", var nombre: String = "", var puntos:Int  = 0) : Serializable
