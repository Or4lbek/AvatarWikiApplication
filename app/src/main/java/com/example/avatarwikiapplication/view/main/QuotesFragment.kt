package com.example.avatarwikiapplication.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.avatarwikiapplication.R


class QuotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = QuotesFragment()
        //fun newInstance() = CharactersFragment()

    }
}