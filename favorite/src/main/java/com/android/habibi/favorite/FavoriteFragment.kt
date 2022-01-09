package com.android.habibi.favorite

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.ui.MovieAdapter
import com.android.habibi.di.favoriteModule
import com.android.habibi.di.settingPreferencesModule
import com.android.habibi.favorite.databinding.FavoriteFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModel()

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    private var listMovie: List<Movie>? = null
    private var typeList: Int? = null

    override fun onStart() {
        super.onStart()
        loadKoinModules(
            listOf(
                settingPreferencesModule,
                favoriteModule
            )
        )
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
        startObserverTypeList()
    }

    private fun startObserverTypeList(){
        viewModel.getTypeListSetting().observe(
            viewLifecycleOwner, {
                typeList = it
                setAdapter()
                startSettingListener(it)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startSettingListener(typeList: Int){
        binding.ivSetting.setOnClickListener {
            showSettingDialog(typeList)
        }
    }

    private fun startObserver(){
        onLoading()
        viewModel.listFavoriteMovie.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                listMovie = it
                setAdapter()
                onSuccess()
            } else
                onEmpty()
        })
    }

    private fun setAdapter(type: Int? = typeList, list: List<Movie>? = listMovie){
        if (type != null && list != null){
            when(type){
                0 -> {
                    movieAdapter = MovieAdapter(list as ArrayList<Movie>, MovieAdapter.VIEW_TYPE_STAGGERED){
                        toDetailMovie(it)
                    }

                    with(binding.rvListMovie){
                        layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                        setHasFixedSize(true)
                        adapter = movieAdapter
                    }
                }
                1 -> {
                    movieAdapter = MovieAdapter(list as ArrayList<Movie>, MovieAdapter.VIEW_TYPE_LINEAR_VERTICAL){
                        toDetailMovie(it)
                    }

                    with(binding.rvListMovie){
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                        adapter = movieAdapter
                    }
                }
            }
        }
    }

    private fun toDetailMovie(it: Movie){
        val toDetailMovie = FavoriteFragmentDirections.actionNavigationFavoriteDynamicFeatureToDetailMovieActivity(it.id)
        binding.root.findNavController().navigate(toDetailMovie)
    }

    private fun onLoading(){
        binding.apply {
            progressBar.visibility = View.VISIBLE
            viewDataEmpty.visibility = View.GONE
            rvListMovie.visibility = View.GONE
            ivSetting.visibility = View.GONE
        }
    }

    private fun onSuccess(){
        binding.apply {
            progressBar.visibility = View.GONE
            viewDataEmpty.visibility = View.GONE
            rvListMovie.visibility = View.VISIBLE
            ivSetting.visibility = View.VISIBLE
        }
    }

    private fun onEmpty(){
        binding.apply {
            progressBar.visibility = View.GONE
            viewDataEmpty.visibility = View.VISIBLE
            rvListMovie.visibility = View.GONE
            ivSetting.visibility = View.GONE
        }
    }

    private fun showSettingDialog(typeList: Int){
        val choices = arrayOf<CharSequence>("Staggered", "List Vertikal")

        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Jenis List")
            .setPositiveButton(
                "Ubah"
            ) { dialog: DialogInterface, _: Int ->
                val checkedItemPosition =
                    (dialog as AlertDialog).listView
                        .checkedItemPosition
                if (checkedItemPosition != AdapterView.INVALID_POSITION) {
                    viewModel.saveTypeListSetting(checkedItemPosition)
                }
            }
            .setNegativeButton("Batal", null)
            .setSingleChoiceItems(choices, typeList, null)
            .show()
    }
}