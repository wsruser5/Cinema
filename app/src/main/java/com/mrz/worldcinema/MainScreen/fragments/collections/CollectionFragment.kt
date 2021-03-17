package com.mrz.worldcinema.MainScreen.fragments.collections

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrz.worldcinema.R

class CollectionFragment : Fragment() {

    private lateinit var collectionViewModel: CollectionViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       collectionViewModel =
            ViewModelProvider(this).get(CollectionViewModel::class.java)
        val root = inflater.inflate(R.layout.collection_fragment, container, false)
        return root
    }

}