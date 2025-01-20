package com.stepikbrowser.feature.bookmarks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stepikbrowser.core.ui.CourseAdapter
import com.stepikbrowser.feature.bookmarks.databinding.BookmarksFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment: Fragment(R.layout.bookmarks_fragment) {
    private val viewModel: BookmarksFragmentViewModel by viewModels()
    private lateinit var adapter: CourseAdapter
    private lateinit var binding: BookmarksFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookmarksFragmentBinding.bind(view)

        showShimmer()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.bookmarkedCourses.observe(viewLifecycleOwner) { courses ->
            adapter.submitList(courses)
            hideShimmer()
        }
    }
    private fun setupRecyclerView() {
        binding.coursesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CourseAdapter ({ course ->
            openCourseUrl(course.url)
        }, { course ->
            viewModel.bookmarkCourse(course, course.bookmarked)
        })
        binding.coursesRecyclerView.adapter = adapter
    }
    private fun openCourseUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Unable to open URL", Toast.LENGTH_SHORT).show()
        }
    }
    private fun showShimmer() {
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout.visibility = View.VISIBLE
    }
    private fun hideShimmer() {
        binding.shimmerLayout.visibility = View.GONE
        binding.shimmerLayout.stopShimmer()
    }
}