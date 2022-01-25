package com.example.avatarwikiapplication.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.model.CharactersItem
import com.squareup.picasso.Picasso

class CharactersAdapter(
    private val characters: List<CharactersItem>,
    private val mContext: Context,
    private val mOnItemNoteListener: OnItemNoteListener
) : RecyclerView.Adapter<CharactersAdapter.MyViewHolder?>() {

    private val defaultImageUrl =
        "https://64.media.tumblr.com/ddfca801ecb3fe76ca973975ddee7a56/2e2872bf6a2e45b3-89/s400x600/60265ea952cf10445a9721fa25a921063c3becea.png"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
        view = layoutInflater.inflate(R.layout.character_record_item, parent, false)
        return MyViewHolder(view, mOnItemNoteListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.name.text = (characters[position].name)
        if (characters[position].photoUrl == null) {
            Picasso.get().load(defaultImageUrl).placeholder(R.drawable.back)
                .error(R.drawable.simple).into(holder.image)
        } else {
            Picasso.get().load(characters[position].photoUrl).placeholder(R.drawable.back)
                .error(R.drawable.simple).into(holder.image)

        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class MyViewHolder(itemView: View, private var noteListener: OnItemNoteListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var name: TextView = itemView.findViewById<TextView>(R.id.textViewTitle)

        var image: ImageView = itemView.findViewById(R.id.imageViewUser)

        override fun onClick(v: View) {
            noteListener.onNoteClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    interface OnItemNoteListener {
        fun onNoteClick(position: Int)
    }
}