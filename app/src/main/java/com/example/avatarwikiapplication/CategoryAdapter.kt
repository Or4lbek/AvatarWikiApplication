package com.example.avatarwikiapplication

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.avatarwikiapplication.databinding.CharacterCategoryItemBinding
import com.example.avatarwikiapplication.model.CharacterCategory
import com.example.avatarwikiapplication.model.CustomerRecord


class CategoryAdapter() :
    RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    var categories = ArrayList<CharacterCategory>()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    lateinit var mContext: Context

    class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding =  CharacterCategoryItemBinding.bind(view)

        fun bind(characterCategory: CharacterCategory, context: Context) = with(binding){
//            textViewUserMail.text = newRecord.mail
//            textViewRecord.text = newRecord.newRecord
            textViewNameCategory.text = characterCategory.nameOfCategory
            imageViewCategory.setImageResource(characterCategory.imageOfCategory)
            cardViewCharacterCategory.setCardBackgroundColor(characterCategory.imageOfCategory)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.example.avatarwikiapplication.R.layout.character_category_item, parent, false)
        mContext = parent.context
        return CategoryAdapter.CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(categories[position],mContext)
//        holder.itemView.startAnimation(AnimationUtils.loadAnimation(mContext, com.example.avatarwikiapplication.R.anim.recycler_view_animation ))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRecord(newCategory: CharacterCategory){
        categories.add(newCategory)
        notifyDataSetChanged()
    }

}