package com.example.mainactivity.search_module.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainactivity.R
import com.example.mainactivity.core.utils.Resource
import com.example.mainactivity.databinding.FragmentArtistBinding
import com.example.mainactivity.search_module.data.models.response.ArtistResponse
import com.example.mainactivity.search_module.ui.activities.DetailsActivity
import com.example.mainactivity.search_module.ui.adapters.SimpleAdapter
import com.example.mainactivity.search_module.utils.DTOConverter
import com.example.mainactivity.search_module.utils.Utils
import com.example.mainactivity.search_module.view_models.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ITEM_ID = "param1"
private const val ITEM_TYPE = "param2"

@AndroidEntryPoint
class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!

    private var _viewModel: DetailsViewModel? = null
    private val viewModel get() = _viewModel!!

    private var listAdapter: SimpleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(requireActivity())[DetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_artist, container, false)
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
        arguments?.getString(ITEM_ID)?.let { viewModel.getArtistDetails(it) }
    }

    private fun setUpObservers() {
        viewModel.artistDetailsLiveData.observe(viewLifecycleOwner) {
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

    private fun setUpViews(data: ArtistResponse?) {
        data?.let { it ->
            binding.descriptionTextView.text = Utils.getMonthlyListeners(it.followers)
            binding.descriptionTextView2.text = ("Popularity " + it.popularity?.toString()) ?: ""
            if (requireActivity() is DetailsActivity) {
                val icon = DTOConverter.getIconFromImage(it.images)
                (requireActivity() as DetailsActivity).apply {
                    setUpToolbar(it.name)
                    setCollapseImage(icon)
                    setCollapseText(it.name)
                }
            }
            val capitalisedList = Utils.getCapitalisedList(it.genres)
            listAdapter?.differ?.submitList(capitalisedList)
        }
    }

    private fun setUpRecyclerView() {
        listAdapter = SimpleAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = listAdapter
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
            ArtistFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM_ID, id)
                    putString(ITEM_TYPE, type)
                }
            }
    }
}