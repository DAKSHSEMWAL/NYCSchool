package com.dakshsemwal.nycschool.data.repository

import com.dakshsemwal.nycschool.data.remote.NYCSchoolDataBaseApi
import com.dakshsemwal.nycschool.data.remote.dto.SATResultDtoItem
import com.dakshsemwal.nycschool.data.remote.dto.SchoolListDTOItem
import com.dakshsemwal.nycschool.domain.repository.NYCSchoolRepository
import javax.inject.Inject

class NYCSchoolRepositoryImpl @Inject constructor(private val api: NYCSchoolDataBaseApi) :
    NYCSchoolRepository {

    override suspend fun getSchoolData(): List<SchoolListDTOItem> {
        return api.getSchoolData()
    }

    override suspend fun getSATResultData(): List<SATResultDtoItem> {
        return api.getSATResultData()
    }

}