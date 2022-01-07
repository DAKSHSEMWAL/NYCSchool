package com.dakshsemwal.nycschool.presentation.ui.home

import com.dakshsemwal.nycschool.data.remote.dto.SchoolListDTOItem

data class SchoolState(
    val isLoading: Boolean = false,
    val schoolListDto: List<SchoolListDTOItem>? = null,
    val error: String = ""
) {
    val sortedSchoolListDTO = schoolListDto?.sortedBy { it.dbn }
}
