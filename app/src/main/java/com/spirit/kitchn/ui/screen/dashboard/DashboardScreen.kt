package com.spirit.kitchn.ui.screen.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.R
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.component.item.recipe.RecipeItemV2
import com.spirit.kitchn.ui.component.item.recipe.RecipeItemVO
import com.spirit.kitchn.ui.theme.KTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    availableRecipes: List<RecipeItemVO>,
    diet: DietVO?,
    onCreateMealPlanClicked: () -> Unit,
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (diet == null) {
                ThereIsNoPlanSection(
                    modifier = Modifier.fillMaxSize(),
                    availableRecipes = availableRecipes,
                    onCreateMealPlanClicked = onCreateMealPlanClicked,
                )
            }
        }
    }
}

@Composable
private fun ThereIsNoPlanSection(
    availableRecipes: List<RecipeItemVO>,
    modifier: Modifier = Modifier,
    onCreateMealPlanClicked: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.height(300.dp),
            painter = painterResource(id = R.drawable.pngegg),
            contentDescription = "images",
            contentScale = ContentScale.FillHeight,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .testTag("textDiet"),
            text = "There is no plan for today...\nPlease, eat some."
        )

        Spacer(modifier = Modifier.height(32.dp))

        AvailableRecipesGrid(
            items = availableRecipes,
            modifier = Modifier.height(220.dp),
        )
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .testTag("textDiet"),
            text = "or"
        )
        CreatePlanButton(
            onClick = onCreateMealPlanClicked,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun AvailableRecipesGrid(
    items: List<RecipeItemVO>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { items.count() })
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
    ) { page ->
        Column(modifier = Modifier.fillMaxSize()) {
            RecipeItemV2(
                item = items[page],
                onItemClicked = {},
            )
        }
    }
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
private fun CreatePlanButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    KButton(
        onClick = onClick,
        modifier = modifier
            .testTag("buttonCreateMealPlan")
            .fillMaxWidth(),
        label = "Plan your meal",
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
            onCreateMealPlanClicked = {},
            availableRecipes = listOf(),
        )
    }
}

@Preview
@Composable
private fun DashboardScreenPreview_withoutDiet() {
    KTheme {
        DashboardScreen(
            diet = null,
            availableRecipes = listOf(
                RecipeItemVO(
                    name = "Meal 1",
                    id = "1",
                    imageUrl = null,
                ),
            ),
            onCreateMealPlanClicked = {},
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