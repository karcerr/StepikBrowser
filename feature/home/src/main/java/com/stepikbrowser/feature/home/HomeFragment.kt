package com.stepikbrowser.feature.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stepikbrowser.core.ui.CourseAdapter
import com.stepikbrowser.feature.home.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.home_fragment) {
    private val viewModel: HomeFragmentViewModel by activityViewModels()
    private lateinit var adapter: CourseAdapter
    private lateinit var binding: HomeFragmentBinding

    private var sortAscending = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)

        showShimmer()
        setupRecyclerView()
        setupObservers()
        setupSortPopupMenu()
        viewModel.authStepik()
        if (viewModel.courseList.value.isNullOrEmpty())
            viewModel.loadCourses()
        setupLoadMoreButton()
        setupSearchFilter()
    }

    private fun setupSortPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.sortTextButton)
        popupMenu.menuInflater.inflate(R.menu.sort_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            val newOrder =
            when (menuItem.itemId) {
                R.id.sort_by_creation_date -> "create_date"
                R.id.sort_by_update_date -> "update_date"
                R.id.sort_by_title -> "title"
                R.id.sort_by_rating -> "creation_date"
                else -> {"title"}
            }
            viewModel.changeCourseOrder(newOrder, sortAscending)
            true
        }
        binding.sortTextButton.setOnClickListener {
            popupMenu.show()
        }
        binding.sortOrderButton.setOnClickListener {
            sortAscending = !sortAscending
            binding.sortOrderButton.background = if (sortAscending)
                AppCompatResources.getDrawable(requireContext() ,R.drawable.sort_asc)
            else
                AppCompatResources.getDrawable(requireContext() ,R.drawable.sort_desc)
            viewModel.changeCourseOrder(viewModel.orderBy, sortAscending)
        }
    }

    private fun setupObservers() {
        viewModel.courseList.observe(viewLifecycleOwner, { courses ->
            adapter.submitList(courses)
            if (courses.isNotEmpty())
                hideShimmer()
            else
                showShimmer()
        })
    }
    private fun setupRecyclerView() {
        binding.coursesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CourseAdapter ({ course ->
            Toast.makeText(requireContext(), "Clicked: ${course.title}", Toast.LENGTH_SHORT).show()
        }, { course ->
            viewModel.bookmarkCourse(course, course.bookmarked)
        })
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
        binding.loadMoreButton.visibility = View.GONE
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout.visibility = View.VISIBLE
    }
    private fun hideShimmer() {
        binding.shimmerLayout.visibility = View.GONE
        binding.shimmerLayout.stopShimmer()
        binding.loadMoreButton.visibility = View.VISIBLE
    }
    private fun setupSearchFilter() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearchQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}