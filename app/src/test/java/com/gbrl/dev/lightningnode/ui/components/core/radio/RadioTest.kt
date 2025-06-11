package com.gbrl.dev.lightningnode.ui.components.core.radio

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gbrl.dev.lightningnode.ui.TestApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class RadioTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `GIVEN radio component WHEN label argument is provided THEN it should be rendered on screen`() {
        val label = "label"

        composeTestRule.setContent {
            Radio(
                label = label,
                type = RadioType.PRIMARY,
                onClick = {}
            )
        }

        composeTestRule.onNodeWithText(label).assertExists().assertIsDisplayed()
    }

    @Test
    fun `GIVEN enabled radio component WHEN onClick argument is provided and performed THEN it should call the defined function`() {
        val label = "label"

        val onClick = mockk<() -> Unit>()
        every { onClick() } returns Unit

        composeTestRule.setContent {
            Radio(
                label = label,
                type = RadioType.PRIMARY,
                enabled = true,
                onClick = onClick
            )
        }

        composeTestRule.onNodeWithText(label).assertExists().performClick()

        verify(atLeast = 1) { onClick() }
    }

    @Test
    fun `GIVEN disable radio component WHEN click action is performed THEN it should not call the defined function`() {
        val label = "label"

        val onClick = mockk<() -> Unit>()
        every { onClick() } returns Unit

        composeTestRule.setContent {
            Radio(
                label = label,
                type = RadioType.PRIMARY,
                enabled = false,
                onClick = onClick
            )
        }

        composeTestRule.onNodeWithText(label).assertExists().performClick()

        verify(atLeast = 0) { onClick() }
    }
}