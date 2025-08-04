package com.horizon.todoappgit.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.horizon.todoappgit.components.BodyMedium
import com.horizon.todoappgit.components.BodyTextField
import com.horizon.todoappgit.components.DesignCardView
import com.horizon.todoappgit.components.TitleTextField
import com.horizon.todoappgit.components.TopAppBarAddNote
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.events.ToDoEvents
import com.horizon.todoappgit.viewmodel.ToDoViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddNoteRoute(viewModel: ToDoViewModel, navController: NavController) {

    val state by viewModel.state.collectAsState()
    val onFinish = rememberSaveable { mutableStateOf(false) }
    val changeThemeCard = remember { mutableStateOf(false) }
    val imeVisible = WindowInsets.isImeVisible

    LaunchedEffect(imeVisible) {
        if (imeVisible) onFinish.value = false
        changeThemeCard.value = false
    }

    Scaffold(
        topBar = {
            TopAppBarAddNote(
                navController = navController,
                viewModel = viewModel,
                onFinished = onFinish.value,
                onClickFinished = { onFinish.value = true }
            ) {
                changeThemeCard.value = !changeThemeCard.value
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { pad ->
        AddNoteView(pad, state, viewModel, changeThemeCard.value)
    }
}

@Composable
fun AddNoteView(
    pad: PaddingValues,
    state: ToDoState,
    viewModel: ToDoViewModel,
    selectTheme: Boolean
) {
    Column(
        modifier = Modifier
            .padding(pad)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (selectTheme) DesignCardView(state, viewModel)
        TitleTextField(state, viewModel)
        BodyMedium(
            "Characters: ${state.body.length}",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        BodyTextField(state, viewModel)
    }
}
