package com.example.mainactivity.search_module.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainactivity.R
import com.example.mainactivity.core.utils.Resource
import com.example.mainactivity.databinding.FragmentAlbumBinding
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import com.example.mainactivity.search_module.data.models.response.AlbumResponse
import com.example.mainactivity.search_module.ui.activities.DetailsActivity
import com.example.mainactivity.search_module.ui.adapters.SearchAdapter
import com.example.mainactivity.search_module.utils.DTOConverter
import com.example.mainactivity.search_module.view_models.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val ITEM_ID = "item_id"
private const val ITEM_TYPE = "item_type"
@AndroidEntryPoint
class AlbumFragment : Fragment() {

    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    private var _viewModel: DetailsViewModel? = null
    private val viewModel get() = _viewModel!!

    private var listAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(requireActivity())[DetailsViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {
        setUpData()
        setUpObservers()
        setUpRecyclerView()
    }

    private fun setUpData() {
        arguments?.getString(ITEM_ID)?.let { viewModel.getAlbumDetails(it) }
    }

    private fun setUpObservers() {
        viewModel.albumDetailsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Error -> {


                }

                is Resource.Success -> {
                    setUpViews(it.data)
                }
            }
        }
    }

    private fun setUpViews(data: AlbumResponse?) {
        data?.let { it ->
            binding.titleTextView.text = it.name
            binding.descriptionTextView.text =
                it.artists?.map { artist -> artist.name }?.joinToString(", ")
            binding.description2TextView.text = it.releaseData
            if (requireActivity() is DetailsActivity) {
                val icon = DTOConverter.getIconFromImage(it.images)
                (requireActivity() as DetailsActivity).apply {
                    setUpToolbar(it.name)
                    setCollapseImage(icon)
                }
            }
            val trackDTOList = DTOConverter.getListFromTracks(it.tracks)
            listAdapter?.differ?.submitList(trackDTOList)
        }
    }

    private fun setUpRecyclerView() {
        listAdapter = SearchAdapter(::onItemClick, ::onItemEndIconClick)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = listAdapter
    }

    private fun onItemClick(searchRowComponentModel: SearchRowComponentModel) {
        Toast.makeText(context, "Still cooking!", Toast.LENGTH_SHORT).show()
    }

    private fun onItemEndIconClick(searchRowComponentModel: SearchRowComponentModel) {
        Toast.makeText(context, "Still cooking!", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _viewModel = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String, type: String) =
            AlbumFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM_ID, id)
                    putString(ITEM_TYPE, type)
                }
            }
    }
}