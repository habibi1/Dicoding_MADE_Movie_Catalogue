package com.android.habibi.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.databinding.ItemListMovieBinding
import com.android.habibi.core.utils.setImage

class MovieAdapter(
    private val list: ArrayList<Movie>,
    private val onClick: (Movie) -> Unit
): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemListMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(
            view,
            onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int =
        list.size

    inner class ViewHolder(
        private val binding: ItemListMovieBinding,
        val onClick: (Movie) -> Unit
    ): RecyclerView.ViewHolder(binding.root){

        private var currentItem: Movie? = null

        init {
            binding.root.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(movie: Movie){
            currentItem = movie

            setImage(binding.ivPosterMovie, currentItem!!.posterPath)

            binding.tvTitleMovie.text = movie.title
        }
    }
}