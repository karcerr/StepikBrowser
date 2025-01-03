package com.stepikbrowser.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stepikbrowser.core.ui.databinding.ItemCourseBinding
import com.stepikbrowser.domain.stepik.Course

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
            binding.root.setOnClickListener {
                onItemClicked(course)
            }
        }
    }
}