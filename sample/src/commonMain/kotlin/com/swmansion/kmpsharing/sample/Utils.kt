package com.swmansion.kmpsharing.sample

import androidx.compose.runtime.Composable

const val TEST_IMAGE_WIDTH = 512
const val TEST_IMAGE_HEIGHT = 512

@Composable expect fun createAndSaveTestBitmap(): String?
