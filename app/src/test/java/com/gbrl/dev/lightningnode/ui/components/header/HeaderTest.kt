package com.gbrl.dev.lightningnode.ui.components.header

import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gbrl.dev.lightningnode.R
import com.gbrl.dev.lightningnode.ui.TestApplication
import com.gbrl.dev.lightningnode.ui.test.semantic.drawableId
import com.gbrl.dev.lightningnode.ui.test.semantic.hasDrawable
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class HeaderTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `GIVEN header component WHEN label argument is provided THEN it should be rendered on screen`() =
        runTest {
            val label = "label"

            composeTestRule.setContent {
                Header(
                    label = label
                )
            }

            composeTestRule.onNodeWithText(label).assertExists().assertIsDisplayed()
        }

    @Test
    fun `GIVEN header component WHEN leftIcon argument is provided THEN it should be rendered on screen`() =
        runTest {
            val leftIcon = R.drawable.ic_arrow_left

            composeTestRule.setContent {
                Header(
                    label = "",
                    leftIcon = {
                        Icon(
                            modifier = Modifier.semantics {
                                drawableId = leftIcon
                            },
                            painter = painterResource(leftIcon),
                            contentDescription = null
                        )
                    }
                )
            }

            composeTestRule.onNode(hasDrawable(leftIcon)).assertExists().assertIsDisplayed()
        }

    @Test
    fun `GIVEN header component WHEN onLeftIconClick argument is provided and triggered THEN it should call the defined function`() =
        runTest {
            val leftIcon = R.drawable.ic_arrow_left

            val onLeftIconClick = mockk<() -> Unit>()
            every { onLeftIconClick() } returns Unit

            composeTestRule.setContent {
                Header(
                    label = "",
                    leftIcon = {
                        Icon(
                            modifier = Modifier.semantics {
                                drawableId = leftIcon
                            },
                            painter = painterResource(leftIcon),
                            contentDescription = null
                        )
                    },
                    onLeftIconClick = onLeftIconClick
                )
            }

            composeTestRule.onNode(hasDrawable(leftIcon)).assertExists().performClick()

            verify(atLeast = 1) { onLeftIconClick() }
        }

    @Test
    fun `GIVEN header component WHEN rightIcon argument is provided THEN it should be rendered on screen`() =
        runTest {
            val rightIcon = R.drawable.ic_arrow_left

            composeTestRule.setContent {
                Header(
                    label = "",
                    rightIcon = {
                        Icon(
                            modifier = Modifier.semantics {
                                drawableId = rightIcon
                            },
                            painter = painterResource(rightIcon),
                            contentDescription = null
                        )
                    }
                )
            }

            composeTestRule.onNode(hasDrawable(rightIcon)).assertExists().assertIsDisplayed()
        }

    @Test
    fun `GIVEN header component WHEN onRightIconClick argument is provided and triggered THEN it should call the defined function`() =
        runTest {
            val rightIcon = R.drawable.ic_arrow_left

            val onRightIconClick = mockk<() -> Unit>()
            every { onRightIconClick() } returns Unit

            composeTestRule.setContent {
                Header(
                    label = "",
                    rightIcon = {
                        Icon(
                            modifier = Modifier.semantics {
                                drawableId = rightIcon
                            },
                            painter = painterResource(rightIcon),
                            contentDescription = null
                        )
                    },
                    onRightIconClick = onRightIconClick
                )
            }

            composeTestRule.onNode(hasDrawable(rightIcon)).assertExists().performClick()

            verify(atLeast = 1) { onRightIconClick() }
        }
}