package com.stepikbrowser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.stepikbrowser.domain.stepik.Course
import java.util.*

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val coverUrl: String?,
    val summary: String?,
    val description: String?,
    val createDate: Date,
    val updateDate: Date?,
    val rating: Float?,
    val price: Float?
)
fun Course.toEntity() = CourseEntity(
    id = id,
    title = title,
    coverUrl = coverUrl,
    summary = summary,
    description = description,
    createDate = createDate,
    updateDate = updateDate,
    rating = rating,
    price = price,
)

fun CourseEntity.toDomain() = Course(
    id = id,
    title = title,
    coverUrl = coverUrl,
    summary = summary,
    description = description,
    createDate = createDate,
    updateDate = updateDate,
    rating = rating,
    price = price,
    bookmarked = true
)
