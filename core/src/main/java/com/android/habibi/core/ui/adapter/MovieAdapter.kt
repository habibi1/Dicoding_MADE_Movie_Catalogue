package com.android.habibi.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.habibi.core.ui.model.Movie
import com.android.habibi.core.databinding.ItemMovieLinearVerticalBinding
import com.android.habibi.core.databinding.ItemMovieStaggeredBinding
import com.android.habibi.core.databinding.ItemMovieViewPagerBinding
import com.android.habibi.core.utils.setImage

class MovieAdapter(
    private val list: ArrayList<Movie>,
    private val listViewType: Int,
    private val onClick: (Movie) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_STAGGERED = 0
        const val VIEW_TYPE_LINEAR_VERTICAL = 1
        const val VIEW_TYPE_VIEW_PAGER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType){
            VIEW_TYPE_STAGGERED -> {
                val view = ItemMovieStaggeredBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolderStaggered(
                    view,
                    onClick
                )
            }
            VIEW_TYPE_LINEAR_VERTICAL -> {
                val view = ItemMovieLinearVerticalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolderLinearVertical(
                    view,
                    onClick
                )
            }
            else -> {
                val view = ItemMovieViewPagerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolderViewPager(
                    view,
                    onClick
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (listViewType) {
            VIEW_TYPE_STAGGERED -> {
                (holder as ViewHolderStaggered).bind(item)
            }
            VIEW_TYPE_LINEAR_VERTICAL -> {
                (holder as ViewHolderLinearVertical).bind(item)
            }
            VIEW_TYPE_VIEW_PAGER -> {
                (holder as ViewHolderViewPager).bind(item)
            }
        }
    }

    override fun getItemCount(): Int =
        list.size

    override fun getItemViewType(position: Int): Int {
        return listViewType
    }

    inner class ViewHolderStaggered(
        private val binding: ItemMovieStaggeredBinding,
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

    inner class ViewHolderLinearVertical(
        private val binding: ItemMovieLinearVerticalBinding,
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

    inner class ViewHolderViewPager(
        private val binding: ItemMovieViewPagerBinding,
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

            setImage(binding.ivPoster, currentItem!!.posterPath)

            binding.tvTitleMovie.text = movie.title
            binding.ratingBar.rating = movie.voteAverage
        }
    }
}