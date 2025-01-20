package com.stepikbrowser.domain.stepik


import com.google.gson.annotations.SerializedName
import java.util.*

data class Course(
    val id: Int,                // "id"
    var title: String,          // "title" (ru)
    @SerializedName("cover") var coverUrl: String?,
    var summary: String?,       // "summary"
    var description: String?,   // "description"
    @SerializedName("create_date") var createDate: Date,
    @SerializedName("update_date") var updateDate: Date?,
    @SerializedName("canonical_url") var url: String,
    var rating: Float?,         // "review_summary", average
    @SerializedName("display_price") var price: String?,
    var bookmarked: Boolean?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val course = other as Course

        return id == course.id &&
                title == course.title &&
                coverUrl == course.coverUrl &&
                summary == course.summary &&
                description == course.description &&
                createDate == course.createDate &&
                updateDate == course.updateDate &&
                url == course.url &&
                rating == course.rating &&
                price == course.price
    }

    override fun hashCode(): Int {
        return Objects.hash(id, title, coverUrl, summary, description, createDate, updateDate, url, rating, price)
    }
}
