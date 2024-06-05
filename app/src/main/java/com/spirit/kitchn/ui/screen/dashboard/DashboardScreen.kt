package com.spirit.kitchn.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.ui.component.CaloriesSection
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.StreakSection
import com.spirit.kitchn.ui.theme.KTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    diet: DietVO?,
    onCreateDietClicked: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Dashboard") })
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            StreakSection()
            Spacer(modifier = Modifier.height(16.dp))

            CaloriesSection()
            Spacer(modifier = Modifier.height(16.dp))

            if (diet == null) {
                ThereIsNoDietSection(
                    modifier = Modifier.weight(1f),
                    onCreateDietClicked = onCreateDietClicked,
                )
            } else {
                Row {

                }
            }
        }
    }
}

@Composable
private fun ThereIsNoDietSection(
    modifier: Modifier = Modifier,
    onCreateDietClicked: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .testTag("textDiet"),
            text = "You have no diet yet"
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

    CreateDietButton(
        onClick = onCreateDietClicked,
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ShowShoppingListButton(onClick: () -> Unit) {
    KButton(
        onClick = onClick,
        modifier = Modifier
            .testTag("buttonShowShoppingList")
            .fillMaxWidth(),
        label = "Shopping list",
    )
}

@Composable
private fun CreateDietButton(onClick: () -> Unit) {
    KButton(
        onClick = onClick,
        modifier = Modifier
            .testTag("buttonCreateDiet")
            .fillMaxWidth(),
        label = "Create Diet",
    )
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    KTheme {
        DashboardScreen(
            diet = DietVO(
                passedMeals = listOf(
                    MealVO(
                        name = "Meal 1",
                        calories = 100,
                        recipeId = "1",
                        previewImage = "1",
                        state = MealVO.State.EATEN,
                    ),
                    MealVO(
                        name = "Meal 2",
                        calories = 100,
                        recipeId = "1",
                        previewImage = "1",
                        state = MealVO.State.NOT_EATEN,
                    ),
                ),
                focusedMeal = MealVO(
                    name = "Meal 3",
                    calories = 200,
                    recipeId = "1",
                    previewImage = "1",
                    state = MealVO.State.UNDEFINED,
                ),
                nextMeals = listOf(
                    MealVO(
                        name = "Meal 4",
                        calories = 300,
                        recipeId = "1",
                        previewImage = "1",
                        state = MealVO.State.UNDEFINED,
                    ),
                    MealVO(
                        name = "Meal 5",
                        calories = 100,
                        recipeId = "1",
                        previewImage = "1",
                        state = MealVO.State.UNDEFINED,
                    ),
                ),
            ),
            onCreateDietClicked = {},
        )
    }
}

@Preview
@Composable
private fun DashboardScreenPreview_withoutDiet() {
    KTheme {
        DashboardScreen(
            diet = null,
            onCreateDietClicked = {},
        )
    }
}

data class DietVO(
    val passedMeals: List<MealVO>,
    val focusedMeal: MealVO,
    val nextMeals: List<MealVO>,
)

data class MealVO(
    val recipeId: String,
    val previewImage: String,
    val name: String,
    val calories: Int,
    val state: State,
) {
    enum class State {
        UNDEFINED,
        EATEN,
        NOT_EATEN,
    }
}