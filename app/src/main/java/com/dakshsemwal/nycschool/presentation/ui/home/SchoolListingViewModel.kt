package com.dakshsemwal.nycschool.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dakshsemwal.nycschool.common.Resource
import com.dakshsemwal.nycschool.domain.use_case.GetSchoolUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SchoolListingViewModel @Inject constructor(
    private val getMovieUseCase: GetSchoolUseCase
) : ViewModel() {

    private val _state = MutableLiveData(SchoolState())
    val state: LiveData<SchoolState> = _state

    init {
        getSchool()
    }

    private fun getSchool() {
        getMovieUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = SchoolState(schoolListDto = result.data)
                }
                is Resource.Error -> {
                    _state.value = SchoolState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = SchoolState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}