package com.example.avatarwikiapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avatarwikiapplication.databinding.FragmentNewsBinding
import com.google.firebase.database.*
import kotlin.collections.ArrayList


class NewsFeedFragment : Fragment(R.layout.fragment_news) {

    private var binding: FragmentNewsBinding?= null
    private val adapter = RecordAdapter()
    private var USER_KEY:String = "User"
    private lateinit var mDatabase: DatabaseReference
    // my list
    lateinit var newRecords:ArrayList<CustomerRecord>

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
        fun newInstance() = NewsFeedFragment()
    }

    // to avoid memory leak
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun init(){
        newRecords = ArrayList<CustomerRecord>()
        binding?.apply {
            recyclerViewRecords.layoutManager = LinearLayoutManager(activity)
            recyclerViewRecords.adapter = adapter
            adapter.records = newRecords
        }
        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY)

    }

    private fun loadDB(): ValueEventListener {
        val vListener = object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                updateRecords(snapshot)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        return vListener
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecords(snapshot: DataSnapshot) {
        if (newRecords.size > 0 ){
            newRecords.clear()
        }
        for (ds in snapshot.children){
            //ds.child("id").value as String
            var customerId:String = "1"
            var customerMail:String = ds.child("mail").value as String
            var customerRecord:String = ds.child("newRecord").value as String
            var newRecord:CustomerRecord = CustomerRecord(customerId,customerMail,customerRecord)
            newRecords.add(0,newRecord)
        }
        adapter.notifyDataSetChanged()
    }

    private fun getDataFromDB(){
        mDatabase.addValueEventListener(loadDB())
    }
}