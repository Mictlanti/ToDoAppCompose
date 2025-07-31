package com.horizon.todoappgit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.viewmodel.ToDoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarEdit(
    navController: NavController,
    viewModel: ToDoViewModel,
    onFinished: Boolean,
    onClickFinished: () -> Unit,
    selectTheme: () -> Unit
) {

    CenterAlignedTopAppBar(
        title = {
            if (!onFinished) HeadLineLarge(text = "Edit Note") else HeadLineLarge(text = "Personalize")
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
                        "Colors"
                    )
                }
                IconButton(
                    onClick = { viewModel.deleteHomework() }
                ) {
                    Icon(
                        Icons.Default.Delete,
                        "Delete"
                    )
                }
                IconButton(
                    onClick = {
                        viewModel.editNote()
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
fun CardDesign(title: String, body: String, state: ToDoState) {

    val lengthBody = if (body.length > 22) body.take(20) + " ..." else body
    val animatedColor by animateColorAsState(targetValue = colorCards(state.colorCard), label = "cardColor")

    ElevatedCard(
        onClick = {  },
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = animatedColor)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            BodyLarge(title)
            BodyMedium(lengthBody)
        }
    }
}

@Composable
fun ErrorViewComponent(pad: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(pad)
    ) {
        BodyLarge("Error to load")
        BodyMedium("Please close and try again")
    }
}