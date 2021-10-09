package com.example.avatarwikiapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.avatarwikiapplication.databinding.RecordItemBinding

class RecordAdapter: RecyclerView.Adapter<RecordAdapter.RecordHolder>() {

    var recordList = ArrayList<NewRecord>()

    class RecordHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = RecordItemBinding.bind(item)
        fun bind(newRecord: NewRecord) = with(binding){
            textViewUserMail.text = newRecord.userName
            textViewRecord.text = newRecord.record
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
//        TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        return RecordHolder(view)
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
//        TODO("Not yet implemented")
        holder.bind(recordList[position])
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return recordList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addRecord(newRecord: NewRecord){
        recordList.add(newRecord)
        notifyDataSetChanged()
    }
}