package com.example.avatarwikiapplication.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.FragmentCharactersBinding
import com.example.avatarwikiapplication.model.CharacterCategory
import com.example.avatarwikiapplication.model.CharactersItem
import com.example.avatarwikiapplication.view.adapters.CategoryAdapter
import com.example.avatarwikiapplication.view.adapters.CharactersAdapter
import com.example.avatarwikiapplication.viewmodel.CharactersViewModel

class CharactersFragment : Fragment(R.layout.fragment_characters),
    CharactersAdapter.OnItemNoteListener, CategoryAdapter.OnItemNoteListener {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    var newCategories: ArrayList<CharacterCategory> = ArrayList()
    private val categoryAdapter = CategoryAdapter(this)

    private var charactersList: ArrayList<CharactersItem> = ArrayList()
    lateinit var charactersAdapter: CharactersAdapter
    private lateinit var linearLayoutManager: GridLayoutManager
    lateinit var viewModel: CharactersViewModel

    private var page = 0
    private var limit = 24

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharactersBinding.bind(view)
        init()
        initRecyclerView()
        updateRecords()
    }

    private fun init() {
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.apply {

            recyclerViewCharacterCategory.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = categoryAdapter

            }
        }
    }

    private fun initRecyclerView() {
        linearLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewCharacters.layoutManager = linearLayoutManager
        charactersAdapter = CharactersAdapter(charactersList, requireContext(), this)
        binding.recyclerViewCharacters.adapter = charactersAdapter

        if (charactersList.isEmpty()) {
            binding.progressBarCharacter.visibility = View.VISIBLE
            initViewModel()
        }

        binding.recyclerViewCharacters.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = linearLayoutManager.childCount
                val postVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = charactersAdapter.itemCount

                if (!viewModel.isLoading) {
                    if (visibleItemCount + postVisibleItem >= total) {
                        if (page < limit) {
                            page++
                        }
                        viewModel.makeApiCall(page)
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged", "FragmentLiveDataObserve")
    fun initViewModel() {
        viewModel =
            ViewModelProvider(this)[CharactersViewModel::class.java]
        viewModel.getLiveDataObserver().observe(this, Observer {
            if (it != null) {
                binding.progressBarCharacter.visibility = View.GONE
                for (character in it) {
                    charactersList.add(character)
                }
                charactersAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Error in getting list...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        if (page < limit) {
            page++
        }
        viewModel.makeApiCall(page)
    }

    private fun updateRecords() {
        newCategories.add(CharacterCategory("All characters", R.drawable.character2, R.color.main))
        newCategories.add(
            CharacterCategory(
                "Fire characters",
                R.drawable.firebending,
                R.color.roku
            )
        )
        newCategories.add(
            CharacterCategory(
                "Earth characters", R.drawable.earthbending,
                R.color.kioshi
            )
        )
        newCategories.add(
            CharacterCategory(
                "Water characters", R.drawable.waterbending,
                R.color.korra
            )
        )
        newCategories.add(CharacterCategory("Air characters", R.drawable.air_char, R.color.aang))


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

    override fun onNoteClick(position: Int) {
        val character: CharactersItem = charactersList[position]
        val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
            character.name.toString(),
            character
        )
        findNavController().navigate(action)
    }

    override fun OnItemNoteCategoryListener(position: Int) {
        if (position == 1) {
            viewModel.makeApiCallForFireNationCharacters()
        }
    }

}