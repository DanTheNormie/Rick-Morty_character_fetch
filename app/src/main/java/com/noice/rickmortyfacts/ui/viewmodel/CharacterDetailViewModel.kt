package com.noice.rickmortyfacts.ui.viewmodel

import androidx.lifecycle.*
import com.noice.rickmortyfacts.model.CharacterModel
import com.noice.rickmortyfacts.utils.NetworkCallStatus
import com.noice.rickmortyfacts.repostiory.CharacterDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {

    private val characterDetailRepo = CharacterDetailRepository()

    private val _characterData = MutableLiveData<NetworkCallStatus<CharacterModel>>()
    val characterModel:LiveData<NetworkCallStatus<CharacterModel>> = _characterData

    init {
        _characterData.postValue(NetworkCallStatus.loading())
        refreshCharacterData(1)
    }
    fun refreshCharacterData(characterId:Int){
        viewModelScope.launch(Dispatchers.IO){
            val characterData = characterDetailRepo.getCharacterbyId(characterId)
            _characterData.postValue(characterData)
        }
    }

}