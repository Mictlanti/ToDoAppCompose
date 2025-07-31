package com.horizon.todoappgit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.viewmodel.ToDoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarEdit(
    text: String = "To Do App",
    navController: NavController,
    viewModel: ToDoViewModel
) {
    CenterAlignedTopAppBar(
        title = {
            HeadLineLarge(text = text, fontWeight = FontWeight.W600)
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBackIos,
                    "Arrow Back"
                )
            }
        },
        actions = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    viewModel.deleteHomework()
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.Default.Delete,
                        "Done"
                    )
                }
                IconButton(onClick = {
                    viewModel.editNote()
                    navController.popBackStack()
                }) {
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

    val lengthBody = if (body.length > 20) body.take(20) + " ..." else body
    val animatedColor by animateColorAsState(targetValue = colorCards(state), label = "cardColor")

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