package com.example.avatarwikiapplication.view.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.CharacterDetailFragmentBinding
import com.example.avatarwikiapplication.model.CharactersItem
import com.example.avatarwikiapplication.viewmodel.CharacterDetailViewModel

class CharacterDetailFragment : Fragment(R.layout.character_detail_fragment) {


    companion object {
        fun newInstance() = CharacterDetailFragment()
    }

    private var _binding: CharacterDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: CharacterDetailFragmentArgs by navArgs()
    private lateinit var viewModel: CharacterDetailViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[CharacterDetailViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = CharacterDetailFragmentBinding.bind(view)
        val character: CharactersItem = args.character
        viewModel = ViewModelProvider(this)[CharacterDetailViewModel::class.java]
        updateUi(character)
    }

    private fun updateUi(character: CharactersItem) {
        Glide
            .with(requireActivity())
            .load(character.photoUrl)
            .centerCrop()
            .into(binding.imageViewLogo);

        with(binding) {
            textViewName.text = character.name
            textViewAffiliation.text = character.affiliation
            textViewAllies.text = character.allies.toString()
                .substring(1, character.allies.toString().length - 1)
            textViewEnemies.text = character.enemies.toString()
                .substring(1, character.enemies.toString().length - 1)
        }
    }


}