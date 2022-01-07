package com.dakshsemwal.nycschool.presentation.ui.sat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dakshsemwal.nycschool.common.Resource
import com.dakshsemwal.nycschool.domain.use_case.GetSchoolSATCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class SchoolSATViewModel @Inject constructor(
    private val getSchoolSATCase: GetSchoolSATCase
) : ViewModel() {

    private val _state = MutableLiveData(SATState())
    val state: LiveData<SATState> = _state

    init {
        getSchoolSAT()
    }

    private fun getSchoolSAT() {
        getSchoolSATCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = SATState(satResultDtoItem = result.data)
                }
                is Resource.Error -> {
                    _state.value = SATState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = SATState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}