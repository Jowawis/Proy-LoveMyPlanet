package com.example.lovemyplanet.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lovemyplanet.R
import com.example.lovemyplanet.adapters.AdapterRecycler

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AuspiciadorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuspiciadorFragment : Fragment(), AdapterRecycler.OnTitleClickListener  {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_auspiciador, container, false)

        var rvVoluntarios = view.findViewById<RecyclerView>(R.id.rvAuspiciador)
        rvVoluntarios.layoutManager = LinearLayoutManager(requireContext())
        rvVoluntarios.adapter = AdapterRecycler(listOf("Ver Actividades","Crear Actividades"),this)
        rvVoluntarios.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))




        return view.rootView
    }

    override fun onTitleClick(position: Int) {
        if (position == 0){
            startActivity(Intent(requireContext(), ActividadActivity::class.java))
        }else if (position == 1){
            startActivity(Intent(requireContext(), CrearActividadActivity::class.java))
        }
    }
}