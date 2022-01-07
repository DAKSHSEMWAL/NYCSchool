package com.dakshsemwal.nycschool.data.remote.dto

data class SATResultDtoItem(
    val dbn: String,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String,
    val school_name: String
){
    override fun toString(): String {
        return "SATResultDtoItem(dbn='$dbn', num_of_sat_test_takers='$num_of_sat_test_takers', sat_critical_reading_avg_score='$sat_critical_reading_avg_score', sat_math_avg_score='$sat_math_avg_score', sat_writing_avg_score='$sat_writing_avg_score', school_name='$school_name')"
    }
}