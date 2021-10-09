package com.example.avatarwikiapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ClipDrawable.VERTICAL
import android.icu.lang.UCharacter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avatarwikiapplication.databinding.FragmentNewsBinding
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class News : Fragment(R.layout.fragment_news) {

//    val scope:CoroutineScope
//    val scope = CoroutineScope(CoroutineName("MyScope"))
    private var binding: FragmentNewsBinding?= null
    private val adapter = RecordAdapter()
    private var USER_KEY:String = "User"
    private lateinit var mDatabase: DatabaseReference
    // my list
    lateinit var newRecordList:ArrayList<NewRecord>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getDataFromDB()
    }

    companion object {

        @JvmStatic
        fun newInstance() = News()
    }

    // to avoid memory leak
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun init(){
        newRecordList = ArrayList<NewRecord>()
        binding?.apply {
            recyclerViewNews.layoutManager = LinearLayoutManager(activity)
            recyclerViewNews.adapter = adapter
//            layoutAnimation(recyclerViewNews)
            adapter.recordList = newRecordList


//            adapter.addRecord(NewRecord("Oralbek","my first record"))
//            adapter.addRecord(NewRecord("Oralbek","my first record"))
//            adapter.addRecord(NewRecord("Oralbek","my first record"))
//            adapter.addRecord(NewRecord("Oralbek","my first record"))
//            adapter.addRecord(NewRecord("Oralbek","my first record"))
//            adapter.addRecord(NewRecord("Oralbek","my first record"))
        }
        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY)

    }

    private fun getDataFromDB(){
//        scope.launch {
//
//        }
        val vListener = object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (newRecordList.size > 0 ){
                    newRecordList.clear()
                }

                for (ds in snapshot.children){
//                    var tipaRecord = ds.getValue(NewRecord::class.java)
                    var mail:String = ds.child("mail").value as String
                    var oldRecord:String = ds.child("newRecord").value as String
                    var newRecord:NewRecord = NewRecord(mail,oldRecord)
                    // asser user != null; in java
                    newRecordList.add(0,newRecord)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        mDatabase.addValueEventListener(vListener)
    }

//    fun layoutAnimation(recyclerView: RecyclerView){
//        var context:Context = recyclerView.context
//        var layoutAnimationController: LayoutAnimationController? = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fail_down)
//        recyclerView.layoutAnimation = layoutAnimationController
//        recyclerView.adapter?.notifyDataSetChanged()
//        recyclerView.scheduleLayoutAnimation()
//    }
}