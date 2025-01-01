package com.stepikbrowser.domain.stepik

import java.util.Date
import com.google.gson.annotations.SerializedName

data class Course(
    val id: Int,                // "id"
    var title: String,          // "title" (ru)
    @SerializedName("cover") var coverUrl: String,
    var summary: String?,       // "summary"
    var description: String?,   // "description"
    @SerializedName("create_date") var createDate: Date?,
    @SerializedName("update_date") var updateDate: Date?,
    var rating: Float?,         // "review_summary", average
)
