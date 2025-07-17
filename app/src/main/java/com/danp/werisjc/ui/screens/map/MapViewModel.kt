package com.danp.werisjc.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danp.werisjc.domain.model.Service
import com.danp.werisjc.domain.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: ServiceRepository
) : ViewModel() {

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services = _services.asStateFlow()

    private val _selectedService = MutableStateFlow<Service?>(null)
    val selectedService = _selectedService.asStateFlow()

    init {
        viewModelScope.launch {
            _services.value = repository.getAllServices()
        }
    }

    fun selectService(service: Service?) {
        _selectedService.value = service
    }
    fun clearSelectedService() {
        _selectedService.value = null
    }
}
