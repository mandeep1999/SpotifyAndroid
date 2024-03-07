package com.example.mainactivity.search_module.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.core.utils.Resource
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import com.example.mainactivity.search_module.data.models.response.SearchResponse
import com.example.mainactivity.search_module.repositories.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {

    private val _searchResponseMutableLiveData: MutableLiveData<Resource<SearchResponse>> =
        MutableLiveData()
    val searchResponseLiveData get() = _searchResponseMutableLiveData

    private var _recentSearchesMutableLiveData: MutableLiveData<List<SearchRowComponentModel>> =
        MutableLiveData()
    val recentSearchesLiveData get() = _recentSearchesMutableLiveData

    private var _searchJob: Job? = null
    private var _recentSearchJob: Job? = null


    fun getSearchResults(searchText: String) {
        _searchResponseMutableLiveData.value = Resource.Loading()
        _searchJob?.cancel()
        _searchJob = viewModelScope.launch(Dispatchers.IO) {
            val response = searchRepository.getSearchResults(searchText)
            withContext(Dispatchers.Main) {
                _searchResponseMutableLiveData.value = response
            }
        }
    }

    fun getRecentSearchesFromDB() {
        _recentSearchJob?.cancel()
        _recentSearchJob = viewModelScope.launch(Dispatchers.IO) {
            val response = searchRepository.getRecentSearches()
            withContext(Dispatchers.Main) {
                _recentSearchesMutableLiveData.value = response
            }
        }
    }

    fun insertRecentSearchIntoDB(searchRowComponentModel: SearchRowComponentModel) {
        viewModelScope.launch(Dispatchers.IO) {
            searchRepository.insertRecentSearchIntoDB(searchRowComponentModel)
        }
    }
}