package com.dakshsemwal.nycschool.data.remote

import com.dakshsemwal.nycschool.common.Common.SAT_RESULT
import com.dakshsemwal.nycschool.common.Common.SCHOOL_RESULT
import com.dakshsemwal.nycschool.data.remote.dto.SATResultDtoItem
import com.dakshsemwal.nycschool.data.remote.dto.SchoolListDTOItem
import retrofit2.http.GET


interface NYCSchoolDataBaseApi {
    //Function to fetch the list of Schools in new york
    @GET(SCHOOL_RESULT)
    suspend fun getSchoolData(): List<SchoolListDTOItem>
    //Function to fetch the list of Schools with their SAT Results
    @GET(SAT_RESULT)
    suspend fun getSATResultData() : List<SATResultDtoItem>

}