package com.horizon.todoappgit.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.horizon.todoappgit.components.BodyLarge
import com.horizon.todoappgit.components.BodyTextField
import com.horizon.todoappgit.components.DesignCardView
import com.horizon.todoappgit.components.ErrorViewComponent
import com.horizon.todoappgit.components.TitleTextField
import com.horizon.todoappgit.components.TopAppBarEdit
import com.horizon.todoappgit.viewmodel.ToDoViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditNoteRoute(navController: NavController, viewModel: ToDoViewModel, idDoc: Int) {

    val showView = remember { mutableStateOf<Boolean?>(null) }
    val changeThemeCard = remember { mutableStateOf(false) }
    val onFinish = rememberSaveable { mutableStateOf(true) }
    val imeVisible = WindowInsets.isImeVisible

    LaunchedEffect(Unit) {
        val wasLoaded = viewModel.getNoteById(idDoc)

        showView.value = wasLoaded
    }

    LaunchedEffect(imeVisible) {
        if (imeVisible) onFinish.value = false
        changeThemeCard.value = false
    }

    when (showView.value) {
        null -> ChargingView()
        true -> {
            EditNoteView(
                navController,
                viewModel,
                onFinish.value,
                changeThemeCard.value,
                onClickFinished = { onFinish.value = true }) {
                changeThemeCard.value = !changeThemeCard.value
            }
        }
        false -> ErrorView(navController)
    }
}

@Composable
fun EditNoteView(
    navController: NavController,
    viewModel: ToDoViewModel,
    onFinish: Boolean,
    changeThemeCard: Boolean,
    onClickFinished: () -> Unit,
    selectTheme: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarEdit(
                navController = navController,
                viewModel = viewModel,
                onFinished = onFinish,
                onClickFinished = { onClickFinished() },
                selectTheme = { selectTheme() }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { pad ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(pad)
        ) {
            if (changeThemeCard) DesignCardView(state, viewModel)
            TitleTextField(state, viewModel)
            BodyTextField(state, viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorView(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    navController.popBackStack()
                },
                title = {
                    BodyLarge("Edit Note")
                }
            )
        }
    ) { pad ->
        ErrorViewComponent(pad)
    }
}

@Composable
fun ChargingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}