package com.android.habibi.favorite

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.habibi.core.ui.DataResource
import com.android.habibi.core.ui.model.Movie
import com.android.habibi.core.ui.adapter.MovieAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set support action bar
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar as Toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_settings -> {
                typeList?.let { showSettingDialog(it) }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun supportMenuItem(isSupport: Boolean){
        //set support options menu
        setHasOptionsMenu(isSupport)
    }

    private fun startObserverTypeList(){
        viewModel.getTypeListSetting().observe(
            viewLifecycleOwner, {
                typeList = it
                setAdapter()
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startObserver(){
        supportMenuItem(false)
        onLoading()
        viewModel.listFavoriteMovie.observe(viewLifecycleOwner, {
            if (it is DataResource.Success) {
                listMovie = it.data
                setAdapter()
                onSuccess()
                supportMenuItem(true)
            } else {
                onEmpty()
                supportMenuItem(false)
            }
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
            viewDataEmpty.root.visibility = View.GONE
            rvListMovie.visibility = View.GONE
        }
    }

    private fun onSuccess(){
        binding.apply {
            progressBar.visibility = View.GONE
            viewDataEmpty.root.visibility = View.GONE
            rvListMovie.visibility = View.VISIBLE
        }
    }

    private fun onEmpty(){
        binding.apply {
            progressBar.visibility = View.GONE
            viewDataEmpty.root.visibility = View.VISIBLE
            rvListMovie.visibility = View.GONE
        }
    }

    private fun showSettingDialog(typeList: Int){
        val choices = arrayOf<CharSequence>(getString(R.string.staggered_grid), getString(R.string.linear_vertical))

        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.type_list))
            .setPositiveButton(
                getString(R.string.set)
            ) { dialog: DialogInterface, _: Int ->
                val checkedItemPosition =
                    (dialog as AlertDialog).listView
                        .checkedItemPosition
                if (checkedItemPosition != AdapterView.INVALID_POSITION) {
                    viewModel.saveTypeListSetting(checkedItemPosition)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .setSingleChoiceItems(choices, typeList, null)
            .show()
    }
}