package com.example.avatarwikiapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.avatarwikiapplication.databinding.RecordItemBinding

class RecordAdapter: RecyclerView.Adapter<RecordAdapter.RecordHolder>() {

    var records = ArrayList<CustomerRecord>()
    lateinit var mContext: Context

    class RecordHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = RecordItemBinding.bind(item)
        fun bind(newRecord: CustomerRecord,context: Context) = with(binding){
            textViewUserMail.text = newRecord.mail
            textViewRecord.text = newRecord.newRecord

            itemView.setOnClickListener(){
                Toast.makeText(context,"pressed ${textViewUserMail.text}",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
//        TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        mContext = parent.context
        return RecordHolder(view)
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
//        TODO("Not yet implemented")
        holder.bind(records[position],mContext)
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.recycler_view_animation ))
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return records.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addRecord(newRecord: CustomerRecord){
        records.add(newRecord)
        notifyDataSetChanged()
    }
}