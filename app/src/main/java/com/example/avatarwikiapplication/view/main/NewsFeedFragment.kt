package com.example.avatarwikiapplication.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.FragmentNewsBinding
import com.example.avatarwikiapplication.model.CustomerRecord
import com.example.avatarwikiapplication.view.adapters.RecordAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class NewsFeedFragment : Fragment(R.layout.fragment_news) {

    private var binding: FragmentNewsBinding? = null
    private val adapter = RecordAdapter()
    private var USER_KEY: String = "User"
    private lateinit var mDatabase: DatabaseReference

    // my list
    lateinit var newRecords: ArrayList<CustomerRecord>

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
        if (newRecords.isEmpty()) {
            binding?.progressBar?.visibility = View.VISIBLE
        }
        getDataFromDB()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFeedFragment()
    }

    private fun init() {
        if ((activity as MainActivity).btnAppBar.visibility == View.GONE) {
            (activity as MainActivity).setBottomAppBarForHome()
        }
        newRecords = ArrayList<CustomerRecord>()
        binding?.apply {
            recyclerViewRecords.layoutManager = LinearLayoutManager(activity)
            recyclerViewRecords.adapter = adapter

            adapter.setOnItemClickListener(object : RecordAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

//                    var oneItemRecord:CustomerRecord = newRecords[position]
//                    var fragment:Fragment = RecordDetailFragment.newInstance(oneItemRecord.mail,oneItemRecord.newRecord)
//
//                    var transaction:FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
////                    transaction.replace(R.id., fragment).addToBackStack(null).commit()
                }
            })
//            recyclerViewRecords.setOnClickListener()
        }
        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY)

    }

    private fun loadDB(): ValueEventListener {
        val vListener = object : ValueEventListener {
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
        if (newRecords.size > 0) {
            newRecords.clear()
        }
        for (ds in snapshot.children) {
            //ds.child("id").value as String
            val customerId: String = "1"
            val customerMail: String = ds.child("mail").value as String
            val customerRecord: String = ds.child("newRecord").value as String
            val newRecord: CustomerRecord = CustomerRecord(customerId, customerMail, customerRecord)
            newRecords.add(0, newRecord)
        }
        binding?.progressBar?.visibility = View.GONE
        adapter.records = newRecords
        adapter.notifyDataSetChanged()
    }

    private fun getDataFromDB() {
        mDatabase.addValueEventListener(loadDB())
    }

}