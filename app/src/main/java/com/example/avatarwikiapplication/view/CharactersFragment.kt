package com.example.avatarwikiapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avatarwikiapplication.CategoryAdapter
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.RecordAdapter
import com.example.avatarwikiapplication.databinding.FragmentCharactersBinding
import com.example.avatarwikiapplication.model.CharacterCategory
import com.example.avatarwikiapplication.model.CustomerRecord
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class CharactersFragment : Fragment(R.layout.fragment_characters) {

    private var _binding:FragmentCharactersBinding ?= null
    private val binding get() = _binding!!
    var newCategories:ArrayList<CharacterCategory> = ArrayList()
    private val categoryAdapter = CategoryAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharactersBinding.bind(view)
        init()
        updateRecords()
    }

    private fun init(){
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.apply {

            recyclerViewCharacterCategory.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                //LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                adapter = categoryAdapter
//                recyclerViewRecords.layoutManager = LinearLayoutManager(activity)
//                recyclerViewRecords.adapter = adapter
//                adapter.records = newRecords
            }
        }
    }
    private fun updateRecords() {
        newCategories.add(CharacterCategory("All characters",R.drawable.character2,R.color.bcg))
        newCategories.add(CharacterCategory("All characters",R.drawable.character2,R.color.bcg))
        newCategories.add(CharacterCategory("All characters",R.drawable.character2,R.color.bcg))
        newCategories.add(CharacterCategory("All characters",R.drawable.character2,
            R.color.teal_700
        ))
        categoryAdapter.categories = newCategories
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharactersFragment()
    }

    // to avoid memory leak
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}