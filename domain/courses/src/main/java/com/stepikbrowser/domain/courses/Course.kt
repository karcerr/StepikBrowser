package com.stepikbrowser.domain.courses

import com.google.type.Date

data class Course(
    val id: Int,                // "id"
    var title: String,          // Stepik API provides "title" (ru) or "title_en"
    var coverUrl: String,       // "cover"
    var description: String?,   // "description"
    var createDate: Date?,      // "create_date"
    var updateDate: Date?,      // "update_date"
    var rating: Float?,         // "review_summary", average
)
