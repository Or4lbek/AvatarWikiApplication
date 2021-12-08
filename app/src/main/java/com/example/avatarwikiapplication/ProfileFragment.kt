package com.example.avatarwikiapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.avatarwikiapplication.databinding.FragmentNewsBinding
import com.example.avatarwikiapplication.databinding.FragmentProfileBinding
import com.example.avatarwikiapplication.databinding.FragmentWriteDataBinding
import com.example.avatarwikiapplication.view.MainActivity


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val mailProfile = (activity as MainActivity).getUserMail().toString()
        val userDate = (activity as MainActivity).getUserDate().toString()
        binding?.textViewProfileMail?.text = mailProfile
        binding?.textViewProfileRegistration?.text = userDate
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}