package com.horizon.todoappgit.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.events.ToDoEvents
import com.horizon.todoappgit.ui.theme.primaryDark
import com.horizon.todoappgit.ui.theme.primaryDarkOther1
import com.horizon.todoappgit.ui.theme.primaryDarkOther2
import com.horizon.todoappgit.ui.theme.primaryLight
import com.horizon.todoappgit.ui.theme.primaryLightOther1
import com.horizon.todoappgit.ui.theme.primaryLightOther2
import com.horizon.todoappgit.ui.theme.tertiaryDark
import com.horizon.todoappgit.ui.theme.tertiaryDarkOther1
import com.horizon.todoappgit.ui.theme.tertiaryDarkOther2
import com.horizon.todoappgit.ui.theme.tertiaryLight
import com.horizon.todoappgit.ui.theme.tertiaryLightOther1
import com.horizon.todoappgit.ui.theme.tertiaryLightOther2
import com.horizon.todoappgit.viewmodel.ToDoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarAddNote(
    navController: NavController,
    viewModel: ToDoViewModel,
    onFinished: Boolean,
    onClickFinished: () -> Unit,
    selectTheme: () -> Unit
) {

    CenterAlignedTopAppBar(
        title = {
            if (!onFinished) HeadLineLarge(text = "Create Note") else HeadLineLarge(text = "Personalize")
        },
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    "Arrow Back"
                )
            }
        },
        actions = {
            if (!onFinished) {
                IconButton(
                    onClick = { onClickFinished() }
                ) {
                    Icon(
                        Icons.Default.Done,
                        "Done"
                    )
                }
            } else {
                IconButton(
                    onClick = { selectTheme() }
                ) {
                    Icon(
                        Icons.Default.PieChart,
                        "Done"
                    )
                }
//                IconButton(
//                    onClick = { onClickFinished() }
//                ) {
//                    Icon(
//                        Icons.Default.Share,
//                        "Done"
//                    )
//                }
                IconButton(
                    onClick = {
                        viewModel.addNote()
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        Icons.Default.Done,
                        "Done"
                    )
                }
            }
        }
    )
}

@Composable
fun TitleTextField(state: ToDoState, viewModel: ToDoViewModel) {
    TextField(
        value = state.title,
        onValueChange = { viewModel.onEvent(ToDoEvents.TitleTextField(it)) },
        label = { Text(text = "Title") },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun BodyTextField(state: ToDoState, viewModel: ToDoViewModel) {
    TextField(
        value = state.body,
        onValueChange = { viewModel.onEvent(ToDoEvents.BodyTextField(it)) },
        label = { Text(text = "Note") },
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun DesignCardView(state: ToDoState, viewModel: ToDoViewModel) {

    val listColors = listOf(
        MaterialTheme.colorScheme.background,
        primaryLight,
        tertiaryLight,
        primaryLightOther1,
        tertiaryLightOther1,
        primaryLightOther2,
        tertiaryLightOther2,
        primaryDark,
        tertiaryDark,
        primaryDarkOther1,
        tertiaryDarkOther1,
        primaryDarkOther2,
        tertiaryDarkOther2,
    )
    val listNameColors = listOf(
        "Default",
        "Olive Green",
        "Deep Teal",
        "Dark Plum",
        "Dark Olive",
        "Teal Blue",
        "Forest Green",
        "Pistachio",
        "Light Turquoise",
        "Light Lavender",
        "Pale Lime Green",
        "Sky Blue",
        "Mint Green Soft"
    )

    Column {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(listColors) { index, color ->
                if (state.colorCard == index) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Card(
                            onClick = { viewModel.onEvent(ToDoEvents.SelectedColor(index)) },
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = color),
                            modifier = Modifier
                                .size(70.dp)
                                .animateContentSize()
                                .border(
                                    1.dp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    shape = CircleShape
                                )
                        ) {}
                        BodyMedium(listNameColors[index], fontSize = 10.sp)
                    }
                } else {
                    Card(
                        onClick = { viewModel.onEvent(ToDoEvents.SelectedColor(index)) },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = .5f)),
                        modifier = Modifier
                            .size(50.dp)
                    ) {}
                }
            }
        }
        CardDesign(state.title, state.body, state)
    }
}