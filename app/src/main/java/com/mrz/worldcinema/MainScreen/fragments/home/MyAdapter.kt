package com.mrz.worldcinema.MainScreen.fragments.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worldcinema.R
import com.example.worldcinema.data.MoviesList
import com.example.worldcinema.data.MoviesListItem
import com.example.worldcinema.data.Post
import com.example.worldcinema.mainScreen.RecyclerItemScreen
import com.mrz.worldcinema.constants.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.item_with_text.view.*
import kotlinx.android.synthetic.main.row_layout.view.*


class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var myList = emptyList<MoviesListItem>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_with_text, parent, false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(Constants.IMG_URL + myList[position].poster)
            .into(holder.itemView.image_preview)
        Log.d("testGif", myList.size.toString())

        holder.itemView.content.setOnClickListener {
            val context=holder.itemView.content.context
            val intent = Intent( context, RecyclerItemScreen::class.java)
            //старт активити
            context.startActivity(intent)
        }
    }

    fun setData(newList: List<MoviesListItem>) {
        myList = newList
        notifyDataSetChanged()
    }

}