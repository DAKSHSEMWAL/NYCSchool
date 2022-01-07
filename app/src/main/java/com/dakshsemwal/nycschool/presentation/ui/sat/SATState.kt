package com.dakshsemwal.nycschool.presentation.ui.sat

import android.util.Log
import com.dakshsemwal.nycschool.data.remote.dto.SATResultDtoItem

data class SATState(
    val isLoading: Boolean = false,
    val satResultDtoItem: List<SATResultDtoItem>? = null,
    val error: String = ""
) {
    fun getSchoolSat(dbn: String): SATResultDtoItem? {
        val test = findItem(dbn)
        Log.e("TAG",dbn)
        return test
    }

    private fun findItem(dbn: String) = satResultDtoItem?.firstOrNull {
        it.dbn == dbn
    }
}



