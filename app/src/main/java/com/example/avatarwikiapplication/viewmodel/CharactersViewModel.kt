package com.example.avatarwikiapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avatarwikiapplication.model.CharactersItem
import com.example.avatarwikiapplication.model.network.RetroInstance
import com.example.avatarwikiapplication.model.network.RetroServiceInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

    private var liveDataList: MutableLiveData<List<CharactersItem>> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<List<CharactersItem>> {
        return liveDataList
    }

    fun makeApiCall() {
        viewModelScope.launch(Dispatchers.IO) {
            val retroInstance = RetroInstance.getRetrofitInstance()
            val retroService = retroInstance.create(RetroServiceInterface::class.java)
            val call = retroService.getCharacters()
            liveDataList.postValue(call)
        }
    }

    fun makeApiCallForFireNationCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val retroInstance = RetroInstance.getRetrofitInstance()
            val retroService = retroInstance.create(RetroServiceInterface::class.java)
            val call = retroService.getFireNationCharacters()
            liveDataList.postValue(call)
        }
    }
}