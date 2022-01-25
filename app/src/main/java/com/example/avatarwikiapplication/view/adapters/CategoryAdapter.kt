package com.example.avatarwikiapplication.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.CharacterCategoryItemBinding
import com.example.avatarwikiapplication.model.CharacterCategory


class CategoryAdapter(
    private val mOnItemNoteListener: CategoryAdapter.OnItemNoteListener
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    var categories = ArrayList<CharacterCategory>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    lateinit var mContext: Context

    class CategoryHolder(
        view: View,
        private var noteCategoryListener: CategoryAdapter.OnItemNoteListener
    ) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        val binding = CharacterCategoryItemBinding.bind(view)

        fun bind(characterCategory: CharacterCategory, context: Context) = with(binding) {

            textViewNameCategory.text = characterCategory.nameOfCategory
            imageViewCategory.setImageResource(characterCategory.imageOfCategory)
            suka.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    characterCategory.colorOfCategory
                )
            )
        }

        override fun onClick(v: View) {
            noteCategoryListener.OnItemNoteCategoryListener(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_category_item, parent, false)
        mContext = parent.context
        return CategoryHolder(view, mOnItemNoteListener)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(categories[position], mContext)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRecord(newCategory: CharacterCategory) {
        categories.add(newCategory)
        notifyDataSetChanged()
    }

    interface OnItemNoteListener {
        fun OnItemNoteCategoryListener(position: Int)
    }
}