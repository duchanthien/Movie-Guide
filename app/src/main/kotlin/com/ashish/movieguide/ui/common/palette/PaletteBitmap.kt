package com.ashish.movieguide.ui.common.palette

import android.graphics.Bitmap
import android.support.v7.graphics.Palette

/**
 * A simple wrapper for a [Palette] and a [Bitmap].
 */
data class PaletteBitmap(val bitmap: Bitmap, val palette: Palette)