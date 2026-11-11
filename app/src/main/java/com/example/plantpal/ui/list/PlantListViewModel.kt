package com.example.plantpal.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantpal.data.Plant
import com.example.plantpal.data.PlantDatabase
import com.example.plantpal.data.PlantRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface PlantListUiState {
    data object Loading : PlantListUiState
    data class Success(val plants: List<Plant>) : PlantListUiState
}

class PlantListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PlantRepository(PlantDatabase.getInstance(application).plantDao())

    val uiState: StateFlow<PlantListUiState> = repository.plants
        .map<List<Plant>, PlantListUiState> { plants -> PlantListUiState.Success(plants) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PlantListUiState.Loading
        )
}