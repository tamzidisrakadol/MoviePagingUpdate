package com.example.moviewithpaging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviewithpaging.databinding.ListItemBinding
import com.example.moviewithpaging.model.MovieModel
import com.example.moviewithpaging.utility.Constraints

class MovieAdapter(private val context: Context) :
    PagingDataAdapter<MovieModel, MovieAdapter.MovieViewHolder>(comparator) {


    class MovieViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    companion object {
        private val comparator = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }

        }

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.binding.movieNameTV.text = item.title
           // val posterUrl = Constraints.posterUrl + item.posterPath
          //  Glide.with(context).load(posterUrl).into(holder.binding.movieImgview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }
}