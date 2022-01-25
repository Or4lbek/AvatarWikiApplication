package com.example.avatarwikiapplication.view.main.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.view.main.MainActivity


class AboutMeFragment : Fragment(R.layout.fragment_about_me) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        (activity as MainActivity).hideBottomAppBar()
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = AboutMeFragment()
    }
}