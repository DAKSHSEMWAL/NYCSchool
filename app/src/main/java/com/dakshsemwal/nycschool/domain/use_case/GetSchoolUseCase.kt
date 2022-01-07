package com.dakshsemwal.nycschool.domain.use_case

import com.dakshsemwal.nycschool.common.Resource
import com.dakshsemwal.nycschool.data.remote.dto.SchoolListDTOItem
import com.dakshsemwal.nycschool.domain.repository.NYCSchoolRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Use Case Class to get SCHOOL LIST present in NYC
 */
class GetSchoolUseCase @Inject constructor(private val repository: NYCSchoolRepository) {
    operator fun invoke(): Flow<Resource<List<SchoolListDTOItem>>> = flow {
        try {
            emit(Resource.Loading<List<SchoolListDTOItem>>())
            val school = repository.getSchoolData()
            emit(Resource.Success<List<SchoolListDTOItem>>(school))
        } catch (e: HttpException) {
            emit(Resource.Error<List<SchoolListDTOItem>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<SchoolListDTOItem>>("Couldn't reach server.Check your internet connection "))
        }
    }
}
