package com.example.mainactivity.search_module.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.core.utils.Resource
import com.example.mainactivity.search_module.data.models.response.AlbumResponse
import com.example.mainactivity.search_module.data.models.response.ArtistResponse
import com.example.mainactivity.search_module.repositories.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val detailsRepository: DetailsRepository) :
    ViewModel() {

    private var _albumDetailsMutableLiveData: MutableLiveData<Resource<AlbumResponse>> =
        MutableLiveData(
        )
    val albumDetailsLiveData: LiveData<Resource<AlbumResponse>> get() = _albumDetailsMutableLiveData

    private var _artistDetailsMutableLiveData: MutableLiveData<Resource<ArtistResponse>> = MutableLiveData()
    val artistDetailsLiveData: LiveData<Resource<ArtistResponse>> get() = _artistDetailsMutableLiveData

    /**
     * Function to get the details of an album, by using the album id
     * @param id -> The album id, on which the api call will be made.
     * The function posts the result on a live data, which is being observed
     * by the fragment.
     */
    fun getAlbumDetails(id: String) {
        _albumDetailsMutableLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = detailsRepository.getAlbumDetails(id)
            withContext(Dispatchers.Main) {
                _albumDetailsMutableLiveData.value = response
            }
        }
    }

    /**
     * Function to get the details of an artist, by using the artist id
     * @param id -> The artist id, on which the api call will be made.
     * The function posts the result on a live data, which is being observed
     * by the fragment.
     */
    fun getArtistDetails(id: String){
        _artistDetailsMutableLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = detailsRepository.getArtistDetails(id)
            withContext(Dispatchers.Main){
                _artistDetailsMutableLiveData.value = response
            }
        }
    }
}
