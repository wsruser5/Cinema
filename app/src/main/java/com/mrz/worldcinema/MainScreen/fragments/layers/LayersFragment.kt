package com.mrz.worldcinema.MainScreen.fragments.layers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrz.worldcinema.MainScreen.fragments.home.HomeViewModel
import com.mrz.worldcinema.R

class LayersFragment : Fragment() {

    private lateinit var layersViewModel: LayersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layersViewModel =
            ViewModelProvider(this).get(LayersViewModel::class.java)
        val root = inflater.inflate(R.layout.layers_fragment, container, false)
        return root
    }


}