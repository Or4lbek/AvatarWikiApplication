package com.example.avatarwikiapplication.view.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.CharacterDetailFragmentBinding
import com.example.avatarwikiapplication.model.CharactersItem
import com.example.avatarwikiapplication.viewmodel.CharacterDetailViewModel
import com.squareup.picasso.Picasso

class CharacterDetailFragment : Fragment(R.layout.character_detail_fragment) {


    companion object {
        fun newInstance() = CharacterDetailFragment()
    }

    private var _binding: CharacterDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: CharacterDetailFragmentArgs by navArgs()
    private lateinit var viewModel: CharacterDetailViewModel
    private val defaultImageUrl =
        "https://64.media.tumblr.com/ddfca801ecb3fe76ca973975ddee7a56/2e2872bf6a2e45b3-89/s400x600/60265ea952cf10445a9721fa25a921063c3becea.png"

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

        if (character.photoUrl == null) {
            Picasso.get().load(defaultImageUrl).placeholder(R.drawable.back)
                .error(R.drawable.simple).into(binding.imageViewLogo)
        } else {
            Picasso.get().load(character.photoUrl).placeholder(R.drawable.back)
                .error(R.drawable.simple).into(binding.imageViewLogo)

        }


        with(binding) {
            textViewName.text = character.name!!
            textViewAffiliation.text = character.affiliation?.toString()
            textViewAllies.text = character.allies.toString()
                .substring(1, character.allies.toString().length - 1)
            textViewEnemies.text = character.enemies.toString()
                .substring(1, character.enemies.toString().length - 1)
            if (character.allies.isEmpty()) {
                cardView.visibility = View.GONE
            }
            if (character.enemies.isEmpty()) {
                cardView5.visibility = View.GONE
            }
        }
    }


}