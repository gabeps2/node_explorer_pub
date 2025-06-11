package com.gbrl.dev.lightningnode.ui.components.container.node_card

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gbrl.dev.lightningnode.R
import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.ui.TestApplication
import com.gbrl.dev.lightningnode.ui.test.semantic.hasDrawable
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class NodeCardTest {
    private lateinit var node: NodeUiModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        node = NodeUiModel(
            publicKey = "123",
            alias = "Node 1",
            channels = "23620",
            channelsRaw = 0,
            capacity = "285.48992",
            capacityRaw = 0.0,
            firstSeen = "03/15/2022 05:39",
            firstSeenRaw = 0,
            updatedAt = "03/15/2022 05:39",
            updatedAtRaw = 0,
            city = "Boardman",
            country = "EUA",
            isFavorite = false
        )
    }

    @Test
    fun `GIVEN a node card component WHEN it is rendered on screen THEN it should show the node alias`() {

        composeTestRule.setContent {
            NodeCard(
                node = node
            )
        }

        composeTestRule.onNodeWithText(text = node.alias).assertExists().assertIsDisplayed()
    }

    @Test
    fun `GIVEN a node card component WHEN it is rendered on screen THEN it should show the node channels`() {

        composeTestRule.setContent {
            NodeCard(
                node = node
            )
        }

        composeTestRule.onNodeWithText(text = node.channels).assertExists().assertIsDisplayed()
    }

    @Test
    fun `GIVEN a node card component WHEN it is rendered on screen THEN it should show the node capacity with BTC suffix`() {

        composeTestRule.setContent {
            NodeCard(
                node = node
            )
        }

        val suffix = " BTC"

        composeTestRule.onNodeWithText(text = node.capacity + suffix).assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun `GIVEN a node card component WHEN it is rendered on screen THEN it should show the country country`() {

        composeTestRule.setContent {
            NodeCard(
                node = node
            )
        }

        composeTestRule.onNodeWithText(text = node.country).assertExists().assertIsDisplayed()
    }

    @Test
    fun `GIVEN a node card component WHEN position argument is given THEN it should be rendered on screen`() {
        val position = 1
        val positionFormatted = "$position.º"

        composeTestRule.setContent {
            NodeCard(
                node = node,
                position = 1
            )
        }

        composeTestRule.onNodeWithText(text = positionFormatted).assertExists().assertIsDisplayed()
    }

    @Test
    fun `GIVEN a node card component WHEN isFavorite argument is true THEN it should render a filled icon`() {
        val isFavorite = true

        composeTestRule.setContent {
            NodeCard(
                node = node.copy(isFavorite = isFavorite),
            )
        }

        composeTestRule.onNode(hasDrawable(R.drawable.ic_heart_filled)).assertExists()
        composeTestRule.onNode(hasDrawable(R.drawable.ic_heart)).assertDoesNotExist()
    }

    @Test
    fun `GIVEN a node card component WHEN isFavorite argument is false THEN it should render a default icon`() {
        val isFavorite = false

        composeTestRule.setContent {
            NodeCard(
                node = node.copy(isFavorite = isFavorite)
            )
        }

        composeTestRule.onNode(hasDrawable(R.drawable.ic_heart)).assertExists()
        composeTestRule.onNode(hasDrawable(R.drawable.ic_heart_filled)).assertDoesNotExist()
    }
}