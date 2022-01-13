package com.android.habibi.dicoding_made_moviecatalogue.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.habibi.core.ui.model.Movie
import com.android.habibi.core.ui.DataResource
import com.android.habibi.core.ui.adapter.MovieAdapter
import com.android.habibi.dicoding_made_moviecatalogue.databinding.FragmentMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModel()
    private var _binding: FragmentMovieBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObserver()
    }

    private fun startObserver(){
        viewModel.getMovie().observe(viewLifecycleOwner, {
            if (it != null){
                when(it){
                    is DataResource.Loading -> {
                        onLoading()
                    }
                    is DataResource.Success -> {
                        setAdapter(it.data!!)
                        onSuccess()
                    }
                    is DataResource.Error -> {
                        onError()
                    }
                    is DataResource.Empty -> {
                        onEmpty()
                    }
                }
            }
        })
    }

    private fun startErrorListener(){
        binding.viewError.btnTryAgain.setOnClickListener {
            viewModel.refreshData()
        }
    }

    private fun setAdapter(list: List<Movie>){
        movieAdapter = MovieAdapter(list as ArrayList<Movie>, MovieAdapter.VIEW_TYPE_VIEW_PAGER){
            val toDetailMovie = MovieFragmentDirections.actionNavigationMovieToDetailMovieActivity(it.id)
            binding.root.findNavController().navigate(toDetailMovie)
        }

        with(binding.rvListMovie){
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = 100
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
            adapter = movieAdapter
        }
    }

    private fun onLoading(){
        binding.apply {
            progressCircular.visibility = View.VISIBLE
            rvListMovie.visibility = View.GONE
            viewError.root.visibility = View.GONE
            viewDataEmpty.root.visibility = View.GONE
        }
    }

    private fun onSuccess(){
        binding.apply {
            progressCircular.visibility = View.GONE
            rvListMovie.visibility = View.VISIBLE
            viewError.root.visibility = View.GONE
            viewDataEmpty.root.visibility = View.GONE
        }
    }

    private fun onError(){
        binding.apply {
            progressCircular.visibility = View.GONE
            rvListMovie.visibility = View.GONE
            viewError.root.visibility = View.VISIBLE
            viewDataEmpty.root.visibility = View.GONE
        }

        startErrorListener()
    }

    private fun onEmpty(){
        binding.apply {
            progressCircular.visibility = View.GONE
            rvListMovie.visibility = View.GONE
            viewError.root.visibility = View.GONE
            viewDataEmpty.root.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvListMovie.adapter = null
        _binding = null
    }
}