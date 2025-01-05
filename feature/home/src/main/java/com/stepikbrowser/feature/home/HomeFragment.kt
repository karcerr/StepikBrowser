package com.stepikbrowser.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stepikbrowser.core.ui.CourseAdapter
import com.stepikbrowser.domain.stepik.Course
import com.stepikbrowser.feature.home.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.home_fragment) {
    private val viewModel: HomeFragmentViewModel by activityViewModels()
    private lateinit var adapter: CourseAdapter
    private lateinit var binding: HomeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)

        showShimmer()
        setupRecyclerView()
        setupObservers()
        viewModel.authStepik()
        if (viewModel.courseList.value.isNullOrEmpty())
            viewModel.loadCourses()
        setupLoadMoreButton()
    }
    private fun setupObservers() {
        viewModel.courseList.observe(viewLifecycleOwner, { courses ->
            adapter.submitList(courses)
            hideShimmer()
        })
    }
    private fun setupRecyclerView() {
        binding.coursesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CourseAdapter { course ->
            Toast.makeText(requireContext(), "Clicked: ${course.title}", Toast.LENGTH_SHORT).show()
        }
        binding.coursesRecyclerView.adapter = adapter
    }
    private fun setupLoadMoreButton() {
        binding.loadMoreButton.setOnClickListener {
            binding.loadMoreButton.visibility = View.GONE
            showShimmer()
            viewModel.loadNextCoursePage()
        }
    }
    private fun showShimmer() {
        binding.shimmerLayout.startShimmer()
        binding.loadMoreButton.visibility = View.GONE
        binding.shimmerLayout.visibility = View.VISIBLE
    }
    private fun hideShimmer() {
        binding.shimmerLayout.stopShimmer()
        binding.loadMoreButton.visibility = View.VISIBLE
        binding.shimmerLayout.visibility = View.GONE
    }

}