package com.example.avatarwikiapplication.view.main.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.view.main.MainActivity

class LogOutFragment : Fragment(R.layout.fragment_log_out) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        (activity as MainActivity).onClickLogOut()
    }

    companion object {

        @JvmStatic
        fun newInstance() = LogOutFragment()
    }
}