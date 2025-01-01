package com.stepikbrowser.data.stepik

import com.google.gson.annotations.SerializedName
import com.stepikbrowser.domain.stepik.Course
import retrofit2.http.GET
import retrofit2.http.Query

interface StepikApiService {
    @GET("courses")
    suspend fun getCourses(
        @Query("page") page: Int?,
        @Query("order") order: String
    ): StepikResponse
}
data class StepikResponse(
    val courses: List<Course>,
    val meta: MetaData
)
data class MetaData(
    val page: Int,
    @SerializedName("has_next") val hasNext: Boolean
)