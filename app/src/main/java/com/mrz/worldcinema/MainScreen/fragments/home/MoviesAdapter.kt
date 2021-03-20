package com.mrz.worldcinema.MainScreen.fragments.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrz.worldcinema.R
import com.mrz.worldcinema.constants.Constants
import com.mrz.worldcinema.data.MoviesListItem
import kotlinx.android.synthetic.main.movies_item.view.*

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    private var myList = emptyList<MoviesListItem>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movies_item, parent, false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(holder.itemView)
                .load(Constants.IMG_URL + myList[position].poster)
                .into(holder.itemView.image_preview)
        Log.d("testGif", myList.size.toString())

    }

    fun setData(newList: List<MoviesListItem>) {
        myList = newList
        notifyDataSetChanged()
    }

}