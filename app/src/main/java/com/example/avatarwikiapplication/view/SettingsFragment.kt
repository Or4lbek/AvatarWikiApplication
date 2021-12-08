package com.example.avatarwikiapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        init()

    }

    private fun init() {
        binding.changeTheme.setOnClickListener {
            (activity as MainActivity).setTheme(com.example.avatarwikiapplication.R.style.Theme_AvatarWikiApplicationNight)
////            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            (activity as MainActivity).startActivity(
                Intent(
                    requireActivity(),
                    MainActivity::class.java
                )
            )
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

//            when (isNightTheme) {
//                Configuration.UI_MODE_NIGHT_YES ->
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                Configuration.UI_MODE_NIGHT_NO ->
//            }


            val toolbar: Toolbar = (activity as MainActivity).binding.toolbar
//            println(toolbar == null)
            (activity as MainActivity).actionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationIcon(R.drawable.ic_arrow)
//
//            toolbar.setNavigationOnClickListener { (activity as MainActivity).onBackPressed() }

//            (activity as MainActivity).supportActionBar?.run {
//                setDisplayShowHomeEnabled(true)
//                setHomeAsUpIndicator(R.drawable.ic_arrow)
//                println("2")
//            }

//            toolbar.setNavigationIcon(R.drawable.ic_back_button)
//            toolbar.setNavigationOnClickListener(View.OnClickListener { activity!!.onBackPressed() })
        }

        binding.imageButton.setOnClickListener {
            shareApp()
        }
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