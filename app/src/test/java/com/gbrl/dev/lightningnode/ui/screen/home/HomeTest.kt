package com.gbrl.dev.lightningnode.ui.screen.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.ui.TestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class HomeTest() {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var node: NodeUiModel

    @Before
    fun setup() {
        node = NodeUiModel(
            publicKey = "123",
            alias = "Node 1",
            channels = "23620",
            channelsRaw = 23620,
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
    fun `GIVEN node list WHEN RenderUntil is called THEN it should render the expected number of nodes`() {
        val expectedNodes = 1

        val firstNodeAlias = "Node 1"
        val secondNodeAlias = "Node 2"

        val nodes = listOf(
            node.copy(alias = firstNodeAlias),
            node.copy(alias = secondNodeAlias),
        )

        composeTestRule.setContent {
            RenderUntil(
                numOfItems = expectedNodes,
                nodes = nodes,
            ) { }
        }

        composeTestRule.onNodeWithText(firstNodeAlias).assertExists()
        composeTestRule.onNodeWithText(secondNodeAlias).assertDoesNotExist()
    }

    @Test
    fun `GIVEN node list WHEN RenderUntil is called with index equal to 0 THEN it should not render any node`() {
        val expectedNodes = 0

        val firstNodeAlias = "Node 1"
        val secondNodeAlias = "Node 2"

        val nodes = listOf(
            node.copy(alias = firstNodeAlias),
            node.copy(alias = secondNodeAlias),
        )

        composeTestRule.setContent {
            RenderUntil(
                numOfItems = expectedNodes,
                nodes = nodes,
            ) { }
        }

        nodes.forEach {
            composeTestRule.onNodeWithText(it.alias).assertDoesNotExist()
        }
    }
}