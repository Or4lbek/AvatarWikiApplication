package com.example.avatarwikiapplication.view.main.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.FragmentSettingsBinding
import com.example.avatarwikiapplication.view.main.MainActivity


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        init()

    }

    private fun init() {
        (activity as MainActivity).hideBottomAppBar()
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Miras")
            var shareMessage = "\nLet me send this application\n\n"
            shareMessage =
                """
                        ${shareMessage}https://play.google.com/store/apps/details?id=${requireActivity().packageName}
                        
                        
                        """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Choose"))
        } catch (e: Exception) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}