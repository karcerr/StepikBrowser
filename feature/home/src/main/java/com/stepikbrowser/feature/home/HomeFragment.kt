package com.stepikbrowser.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stepikbrowser.core.ui.CourseAdapter
import com.stepikbrowser.domain.stepik.Course
import com.stepikbrowser.feature.home.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.home_fragment) {
    private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var adapter: CourseAdapter
    private lateinit var binding: HomeFragmentBinding

    private val curPage = 1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)


        showShimmer()
        setupRecyclerView()
        setupObservers()
        viewModel.authStepik()
        viewModel.loadCourses(curPage)
    }
    private fun setupObservers() {
        viewModel.courseList.observe(viewLifecycleOwner, { courses ->
            adapter = CourseAdapter(courses) { course ->
                Toast.makeText(requireContext(), "Clicked: ${course.title}", Toast.LENGTH_SHORT).show()
            }
            hideShimmer()
            binding.coursesRecyclerView.adapter = adapter
        })
    }
    private fun setupRecyclerView() {
        binding.coursesRecyclerView.layoutManager = LinearLayoutManager(context)
    }
    private fun showShimmer() {
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout.visibility = View.VISIBLE
    }
    private fun hideShimmer() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
    }

}