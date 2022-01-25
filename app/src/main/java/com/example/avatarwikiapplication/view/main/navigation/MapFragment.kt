package com.example.avatarwikiapplication.view

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.avatarwikiapplication.R


class MapFragment : Fragment(R.layout.fragment_map) {

    lateinit var drawer: Drawer
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            drawer = activity as Drawer
        } catch (e: Exception) {
            Log.d("voca", e.message.toString())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        drawer.unlockDrawer()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }
}

interface Drawer {
    fun lockDrawer()
    fun unlockDrawer()
}