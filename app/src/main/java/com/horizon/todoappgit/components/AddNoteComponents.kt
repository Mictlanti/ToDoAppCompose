package com.horizon.todoappgit.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.events.ToDoEvents
import com.horizon.todoappgit.viewmodel.ToDoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarAddNote(
    navController: NavController,
    viewModel: ToDoViewModel,
    pagerState: PagerState
) {

    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val nextPage = (pagerState.currentPage + 1).coerceIn(0, pagerState.pageCount - 1)
    val lastPage = (pagerState.currentPage - 1).coerceIn(0, pagerState.pageCount - 1)

    CenterAlignedTopAppBar(
        title = {
            when(pagerState.currentPage) {
                0 -> {
                    HeadLineLarge(text = "Create Note")
                }
                1 -> {
                    HeadLineLarge(text = "Personalize")
                }
            }
        },
        navigationIcon = {
            if (pagerState.currentPage != 0) {
                IconButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                lastPage,
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    easing = LinearEasing
                                )
                            )
                        }
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        "Last page"
                    )
                }
            } else {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBackIos,
                        "Arrow Back"
                    )
                }
            }
        },
        actions = {
            when (pagerState.currentPage) {
                0 -> {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (state.title.isNotEmpty() || state.body.isNotEmpty()) {
                                    pagerState.animateScrollToPage(
                                        nextPage,
                                        animationSpec = tween(
                                            durationMillis = 1000,
                                            easing = LinearEasing
                                        )
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForwardIos,
                            "Next Page"
                        )
                    }
                }

                else -> {
                    Icon(
                        Icons.Default.ArrowUpward,
                        ""
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