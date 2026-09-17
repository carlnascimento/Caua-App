package com.example.cauaapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cauaapp.data.local.ThemePreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(private val themePreferences: ThemePreferences) : ViewModel() {
    val isDarkMode: StateFlow<Boolean> = themePreferences.isDarkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun toggleTheme() {
        viewModelScope.launch {
            themePreferences.setDarkMode(!isDarkMode.value)
        }
    }
}

class ThemeViewModelFactory(private val themePreferences: ThemePreferences) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(themePreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
