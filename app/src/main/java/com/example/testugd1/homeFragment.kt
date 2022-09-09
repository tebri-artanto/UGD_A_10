package com.example.testugd1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testugd1.Entity.TempatPariwisata


class homeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : rvTempatPariwisata = rvTempatPariwisata(TempatPariwisata.litsOfKelas)
        val rvPariwisata : RecyclerView = view.findViewById(R.id.rv_tempat_pariwisata)
        rvPariwisata.layoutManager = layoutManager
        rvPariwisata.adapter = adapter
    }
}