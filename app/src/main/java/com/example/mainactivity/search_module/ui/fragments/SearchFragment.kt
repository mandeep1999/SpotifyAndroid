package com.example.mainactivity.search_module.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainactivity.R
import com.example.mainactivity.app_variable.JsonKeys
import com.example.mainactivity.core.utils.Resource
import com.example.mainactivity.databinding.FragmentSearchBinding
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import com.example.mainactivity.search_module.data.models.response.SearchResponse
import com.example.mainactivity.search_module.ui.activities.ComingSoonActivity
import com.example.mainactivity.search_module.ui.activities.DetailsActivity
import com.example.mainactivity.search_module.ui.adapters.SearchAdapter
import com.example.mainactivity.search_module.utils.DTOConverter
import com.example.mainactivity.search_module.utils.Utils
import com.example.mainactivity.search_module.view_models.SearchViewModel
import com.example.mainactivity.utils.general_utils.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var _viewModel: SearchViewModel? = null
    private val viewModel get() = _viewModel!!

    private var recentSearchAdapter: SearchAdapter? = null
    private var newSearchAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecentSearchesFromDB()
    }

    private fun initialise() {
        setUpViews()
        setUpData()
        setUpObservers()
        setUpListeners()
    }

    private fun setUpData() {

    }

    private fun setUpViews() {
        setUpRecyclerViews()
    }

    private fun setUpRecyclerViews() {
        setUpRecentSearchesRecyclerView()
        setUpSearchRecyclerView()
    }

    private fun setUpRecentSearchesRecyclerView() {
        recentSearchAdapter = SearchAdapter(::onSearchItemClick, ::onSearchItemEndIconClick)
        binding.recentRc.layoutManager = LinearLayoutManager(requireContext())
        binding.recentRc.setHasFixedSize(true)
        binding.recentRc.adapter = recentSearchAdapter
        setRecentSearchesData()
    }

    private fun setRecentSearchesData() {

    }

    private fun setUpSearchRecyclerView() {
        newSearchAdapter = SearchAdapter(::onSearchItemClick, ::onSearchItemEndIconClick)
        binding.searchRc.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRc.setHasFixedSize(true)
        binding.searchRc.adapter = newSearchAdapter
    }

    private fun setUpObservers() {
        viewModel.searchResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.loadingAnimation.visibility = View.GONE
                    binding.errorLayout.root.visibility = View.GONE
                    binding.searchRc.visibility = View.VISIBLE
                    setUpSearchData(it.data)
                }

                is Resource.Error -> {
                    binding.loadingAnimation.visibility = View.GONE
                    binding.errorLayout.root.visibility = View.VISIBLE
                    binding.searchRc.visibility = View.GONE
                }

                is Resource.Loading -> {
                    binding.loadingAnimation.visibility = View.VISIBLE
                    binding.errorLayout.root.visibility = View.GONE
                    binding.searchRc.visibility = View.GONE
                }
            }
        }

        viewModel.recentSearchesLiveData.observe(viewLifecycleOwner) {
            setUpRecentSearchesList(it)
        }
    }

    private fun setUpListeners() {
        binding.searchAutoComplete.doOnTextChanged { text, _, _, _ ->
            if (text != null && Utility.isValidText(text.toString())) {
                showHideRecentSearches(text.toString())
                viewModel.getSearchResults(text.toString())
            }
        }

        binding.closeIcon.setOnClickListener {
            binding.searchAutoComplete.text.clear()
            showHideRecentSearches("")
        }

        binding.errorLayout.retryButton.setOnClickListener {
            val text = binding.searchAutoComplete.text
            if (text != null && Utility.isValidText(text.toString())) {
                viewModel.getSearchResults(text.toString())
            }
        }
    }

    private fun showHideRecentSearches(searchText: String) {
        if (searchText.isEmpty() && (binding.recentRc.adapter?.itemCount ?: 0) > 0) {
            binding.recentSearchesCl.visibility = View.VISIBLE
            binding.searchRc.visibility = View.GONE
        } else {
            binding.recentSearchesCl.visibility = View.GONE
            binding.searchRc.visibility = View.VISIBLE
        }
    }

    private fun setUpRecentSearchesList(searchRowComponentModels: List<SearchRowComponentModel>) {
        binding.recentSearchesCl.isVisible = searchRowComponentModels.isEmpty() == false
        binding.searchRc.isVisible = searchRowComponentModels.isEmpty() != false
        recentSearchAdapter?.differ?.submitList(searchRowComponentModels)
    }

    private fun setUpSearchData(data: SearchResponse?) {
        val searchItemList = DTOConverter.getSearchItemList(data)
        newSearchAdapter?.differ?.submitList(searchItemList)
    }

    private fun onSearchItemClick(searchRowComponentModel: SearchRowComponentModel) {
        viewModel.insertRecentSearchIntoDB(searchRowComponentModel)
        if (Utils.isSectionDeveloped(searchRowComponentModel)) {
            Intent(requireActivity(), DetailsActivity::class.java).apply {
                putExtra(JsonKeys.TYPE, searchRowComponentModel.type)
                putExtra(JsonKeys.ID, searchRowComponentModel.id)
            }.let {
                startActivity(it)
            }
        } else {
            startActivity(Intent(requireActivity(), ComingSoonActivity::class.java))
        }
    }

    private fun onSearchItemEndIconClick(searchRowComponentModel: SearchRowComponentModel) {

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewModel = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }
}