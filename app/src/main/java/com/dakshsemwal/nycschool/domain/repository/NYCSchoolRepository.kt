package com.dakshsemwal.nycschool.domain.repository

import com.dakshsemwal.nycschool.data.remote.dto.SATResultDtoItem
import com.dakshsemwal.nycschool.data.remote.dto.SchoolListDTOItem

interface NYCSchoolRepository {

    suspend fun getSchoolData(): List<SchoolListDTOItem>

    suspend fun getSATResultData(): List<SATResultDtoItem>

}
