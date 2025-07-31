package com.horizon.todoappgit.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.horizon.todoappgit.components.BodyTextField
import com.horizon.todoappgit.components.TitleTextField
import com.horizon.todoappgit.components.TopAppBarEdit
import com.horizon.todoappgit.viewmodel.ToDoViewModel

@Composable
fun EditNoteRoute(navController: NavController, viewModel: ToDoViewModel, idDoc: Int) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getNoteById(idDoc)
    }

    Scaffold(
        topBar = {
            TopAppBarEdit("Edit", navController, viewModel)
        },
        modifier = Modifier.fillMaxSize()
    ) { pad ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(pad)
        ) {
            TitleTextField(state, viewModel)
            BodyTextField(state, viewModel)
        }
    }
}

