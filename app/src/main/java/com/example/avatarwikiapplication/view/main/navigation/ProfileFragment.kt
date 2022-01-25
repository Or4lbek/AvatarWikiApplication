package com.example.avatarwikiapplication.view.main.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.FragmentProfileBinding
import com.example.avatarwikiapplication.view.main.MainActivity


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val mailProfile = (activity as MainActivity).getUserMail().toString()
        binding.textViewProfileMail.text = mailProfile
        (activity as MainActivity).hideBottomAppBar()
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}