package com.gbrl.dev.lightningnode.ui.test.semantic

import androidx.annotation.DrawableRes
import androidx.compose.ui.test.SemanticsMatcher

fun hasDrawable(@DrawableRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(DrawableId, id)