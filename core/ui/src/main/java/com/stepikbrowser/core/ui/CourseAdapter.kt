package com.stepikbrowser.core.ui

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stepikbrowser.core.ui.databinding.ItemCourseBinding
import com.stepikbrowser.core.util.DateTimeHelper
import com.stepikbrowser.domain.stepik.Course
import eightbitlab.com.blurview.BlurView
import java.util.*


class CourseAdapter(
    private val items: List<Course>,
    private val onItemClicked: (Course) -> Unit
): RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCourseBinding.inflate(inflater, parent, false)
        return CourseViewHolder(binding, onItemClicked)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class CourseViewHolder(
        private val binding: ItemCourseBinding,
        private val onItemClicked: (Course) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course) {
            binding.title.text = course.title
            binding.description.text = course.description
            binding.price.text = (course.price?: binding.root.context.getString(R.string.free)).toString()
            binding.creationDate.text = DateTimeHelper.getPrintableDate(
                course.createDate,
                DateTimeHelper.DISPLAY_DAY_MONTH_YEAR_GENITIVE_PATTERN,
                TimeZone.getDefault()
            )
            Glide.with(binding.root.context)
                .load(course.coverUrl)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.coverImage)
            binding.root.setOnClickListener {
                onItemClicked(course)
            }
            setupBlurView(binding.ratingBlurView)
            setupBlurView(binding.dateBlurView)
            setupBlurView(binding.bookmarkBlurView)
        }
        private fun setupBlurView(blurView: BlurView) {
            val radius = 16f

            val rootView = binding.relativeLayout

            blurView.setupWith(rootView)
                .setBlurRadius(radius)

            blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
            blurView.clipToOutline = true
        }
    }
}