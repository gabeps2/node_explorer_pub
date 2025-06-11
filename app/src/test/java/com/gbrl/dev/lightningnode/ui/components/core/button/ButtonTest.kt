package com.gbrl.dev.lightningnode.ui.components.core.button

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
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class ButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `GIVEN button component WHEN label argument is provided THEN it should be rendered on screen`() {
        val label = "label"

        composeTestRule.setContent {
            Button(
                label = label,
                type = ButtonType.PRIMARY,
                onClick = {}
            )
        }

        composeTestRule.onNodeWithText(label).assertExists().assertIsDisplayed()
    }

    @Test
    fun `GIVEN enabled button component WHEN onClick argument is provided and performed THEN it should call the defined function`() {
        val label = "label"

        val onClick = mockk<() -> Unit>()
        every { onClick() } returns Unit

        composeTestRule.setContent {
            Button(
                label = label,
                type = ButtonType.PRIMARY,
                enabled = true,
                onClick = onClick
            )
        }

        composeTestRule.onNodeWithText(label).assertExists().performClick()

        verify(atLeast = 1) { onClick() }
    }

    @Test
    fun `GIVEN disable button component WHEN click action is performed THEN it should not call the defined function`() {
        val label = "label"

        val onClick = mockk<() -> Unit>()
        every { onClick() } returns Unit

        composeTestRule.setContent {
            Button(
                label = label,
                type = ButtonType.PRIMARY,
                enabled = false,
                onClick = onClick
            )
        }

        composeTestRule.onNodeWithText(label).assertExists().performClick()

        verify(atLeast = 0) { onClick() }
    }
}