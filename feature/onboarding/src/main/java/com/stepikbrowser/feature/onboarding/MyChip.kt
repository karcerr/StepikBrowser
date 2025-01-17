package com.stepikbrowser.feature.onboarding

import eightbitlab.com.blurview.BlurView

data class MyChip(
    val text: String,
    val blurView: BlurView? = null,
    var isClicked: Boolean = false
)
