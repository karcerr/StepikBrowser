package com.stepikbrowser.feature.onboarding

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class OnboardingFragmentViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _chipList = MutableLiveData<List<MyChip>>()
    val chipList: LiveData<List<MyChip>> get() = _chipList
    init {
        val chipNames = getLocalizedChipNames(context)
        val chipObjects = chipNames.map { name -> MyChip(text = name)}
        _chipList.postValue(chipObjects)
    }

    private fun getLocalizedChipNames(context: Context): List<String> {
        val resourceFields = R.string::class.java.declaredFields
        val chipNames = mutableListOf<String>()

        resourceFields.forEach { field ->
            if (field.name.startsWith("chip_")) {
                try {
                    val resId = field.getInt(null)
                    chipNames.add(context.getString(resId))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return chipNames
    }
}