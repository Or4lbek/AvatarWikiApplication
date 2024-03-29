package com.example.avatarwikiapplication.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.RecordItemBinding
import com.example.avatarwikiapplication.model.CustomerRecord

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.RecordHolder>() {

    var records = ArrayList<CustomerRecord>()
    lateinit var mContext: Context
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    class RecordHolder(item: View, listener: onItemClickListener) : RecyclerView.ViewHolder(item) {
        val binding = RecordItemBinding.bind(item)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(newRecord: CustomerRecord, context: Context) = with(binding) {
            textViewUserMail.text = newRecord.mail
            textViewRecord.text = newRecord.newRecord


//            itemView.setOnClickListener(){
//                Toast.makeText(context,"pressed ${textViewUserMail.text}",Toast.LENGTH_SHORT).show()
//            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        mContext = parent.context
        return RecordHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
        holder.bind(records[position], mContext)
        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(
                mContext,
                R.anim.recycler_view_animation
            )
        )
    }

    override fun getItemCount(): Int {
        return records.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRecord(newRecord: CustomerRecord) {
        records.add(newRecord)
        notifyDataSetChanged()
    }
}