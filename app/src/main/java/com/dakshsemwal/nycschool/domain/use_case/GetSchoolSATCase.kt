package com.dakshsemwal.nycschool.domain.use_case

import com.dakshsemwal.nycschool.common.Resource
import com.dakshsemwal.nycschool.data.remote.dto.SATResultDtoItem
import com.dakshsemwal.nycschool.domain.repository.NYCSchoolRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Use Case Class to get SAT results for the Selected School with Id DBN
 */
class GetSchoolSATCase @Inject constructor(private val repository: NYCSchoolRepository) {
    operator fun invoke(): Flow<Resource<List<SATResultDtoItem>>> = flow {
        try {
            emit(Resource.Loading<List<SATResultDtoItem>>())
            val school = repository.getSATResultData()
            emit(Resource.Success<List<SATResultDtoItem>>(school))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<SATResultDtoItem>>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<SATResultDtoItem>>("Couldn't reach server.Check your internet connection "))
        }
    }
}

