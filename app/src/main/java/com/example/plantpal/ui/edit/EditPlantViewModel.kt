package com.example.plantpal.ui.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.plantpal.data.LightNeed
import com.example.plantpal.data.Plant
import com.example.plantpal.data.PlantDatabase
import com.example.plantpal.data.PlantRepository
import com.example.plantpal.domain.PlantValidator
import com.example.plantpal.ui.navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditPlantUiState(
    val name: String = "",
    val intervalText: String = "7",
    val lightNeed: LightNeed = LightNeed.MEDIUM,
    val nameError: String? = null,
    val intervalError: String? = null,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isFinished: Boolean = false
)

class EditPlantViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val repository = PlantRepository(PlantDatabase.getInstance(application).plantDao())

    // null = "add" mode, otherwise the id of the plant being edited
    private val plantId: Long? = savedStateHandle.get<Long>(Routes.ARG_PLANT_ID)
    private var existingPlant: Plant? = null

    private val _uiState = MutableStateFlow(
        EditPlantUiState(isEditing = plantId != null, isLoading = plantId != null)
    )
    val uiState: StateFlow<EditPlantUiState> = _uiState.asStateFlow()

    init {
        plantId?.let { loadPlant(it) }
    }

    private fun loadPlant(id: Long) {
        viewModelScope.launch {
            val plant = repository.getPlant(id)
            if (plant == null) {
                _uiState.update { it.copy(isLoading = false, isFinished = true) }
                return@launch
            }
            existingPlant = plant
            _uiState.update {
                it.copy(
                    name = plant.name,
                    intervalText = plant.wateringIntervalDays.toString(),
                    lightNeed = plant.lightNeed,
                    isLoading = false
                )
            }
        }
    }

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value, nameError = null) }
    }

    fun onIntervalChange(value: String) {
        val digitsOnly = value.filter { it.isDigit() }.take(3)
        _uiState.update { it.copy(intervalText = digitsOnly, intervalError = null) }
    }

    fun onLightNeedChange(value: LightNeed) {
        _uiState.update { it.copy(lightNeed = value) }
    }

    fun save() {
        val state = _uiState.value
        // Ignore taps while a save is running, so one tap can never create two plants.
        if (state.isSaving || state.isLoading) return

        val nameError = PlantValidator.validateName(state.name)
        val intervalError = PlantValidator.validateInterval(state.intervalText)
        if (nameError != null || intervalError != null) {
            _uiState.update { it.copy(nameError = nameError, intervalError = intervalError) }
            return
        }

        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            val name = state.name.trim()
            val days = state.intervalText.trim().toInt()
            val existing = existingPlant
            if (existing == null) {
                repository.addPlant(
                    Plant(name = name, wateringIntervalDays = days, lightNeed = state.lightNeed)
                )
            } else {
                repository.updatePlant(
                    existing.copy(name = name, wateringIntervalDays = days, lightNeed = state.lightNeed)
                )
            }
            _uiState.update { it.copy(isSaving = false, isFinished = true) }
        }
    }

    fun delete() {
        val plant = existingPlant ?: return
        if (_uiState.value.isSaving) return

        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            repository.deletePlant(plant)
            _uiState.update { it.copy(isSaving = false, isFinished = true) }
        }
    }
}