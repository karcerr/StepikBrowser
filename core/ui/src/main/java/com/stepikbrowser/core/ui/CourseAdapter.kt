package com.stepikbrowser.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stepikbrowser.core.ui.databinding.ItemCourseBinding
import com.stepikbrowser.core.util.DateTimeHelper
import com.stepikbrowser.domain.stepik.Course
import eightbitlab.com.blurview.BlurView
import java.util.*


class CourseAdapter(
    private val onItemClicked: (Course) -> Unit,
    private val onBookmarkButtonClicked: (Course) -> Unit
): RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    private val items = mutableListOf<Course>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCourseBinding.inflate(inflater, parent, false)
        return CourseViewHolder(binding, onItemClicked, onBookmarkButtonClicked)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun submitList(courseList: List<Course>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = items.size
            override fun getNewListSize() = courseList.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].id == courseList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == courseList[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(courseList)
        diffResult.dispatchUpdatesTo(this)
    }

    class CourseViewHolder(
        private val binding: ItemCourseBinding,
        private val onItemClicked: (Course) -> Unit,
        private val onBookmarkButtonClicked: (Course) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.title.text = course.title
            binding.description.text = course.description
            binding.price.text = (course.price ?: binding.root.context.getString(R.string.free)).toString()
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
            updateBookmarkButtonTint(course)

            binding.bookmarkButton.setOnClickListener {
                onBookmarkButtonClicked(course)
                course.bookmarked = !(course.bookmarked?: false)
                updateBookmarkButtonTint(course)
            }

            setupBlurView(binding.ratingBlurView)
            setupBlurView(binding.dateBlurView)
            setupBlurView(binding.bookmarkBlurView)
        }

        private fun updateBookmarkButtonTint(course: Course) {
            binding.bookmarkButton.backgroundTintList = if (course.bookmarked == true) {
                ContextCompat.getColorStateList(binding.root.context, R.color.green)
            } else {
                null
            }
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