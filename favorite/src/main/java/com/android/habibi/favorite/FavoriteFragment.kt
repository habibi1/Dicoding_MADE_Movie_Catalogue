package com.android.habibi.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.ui.MovieAdapter
import com.android.habibi.di.favoriteModule
import com.android.habibi.favorite.databinding.FavoriteFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModel()

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    override fun onStart() {
        super.onStart()
        loadKoinModules(favoriteModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        startObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startObserver(){
        onLoading()
        viewModel.listFavoriteMovie.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                setAdapter(it)
                onSuccess()
            } else
                onEmpty()
        })
    }

    private fun setAdapter(list: List<Movie>){
        movieAdapter = MovieAdapter(list as ArrayList<Movie>){
            val toDetailMovie = FavoriteFragmentDirections.actionNavigationFavoriteDynamicFeatureToDetailMovieActivity(it.id)
            binding.root.findNavController().navigate(toDetailMovie)
        }

        with(binding.rvListMovie){
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun onLoading(){
        binding.apply {
            progressBar.visibility = View.VISIBLE
            viewDataEmpty.visibility = View.GONE
            rvListMovie.visibility = View.GONE
        }
    }

    private fun onSuccess(){
        binding.apply {
            progressBar.visibility = View.GONE
            viewDataEmpty.visibility = View.GONE
            rvListMovie.visibility = View.VISIBLE
        }
    }

    private fun onEmpty(){
        binding.apply {
            progressBar.visibility = View.GONE
            viewDataEmpty.visibility = View.VISIBLE
            rvListMovie.visibility = View.GONE
        }
    }

}