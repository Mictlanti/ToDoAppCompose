package com.horizon.todoappgit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.horizon.todoappgit.navigation.Navigation
import com.horizon.todoappgit.ui.theme.ToDoAppGitTheme
import com.horizon.todoappgit.ui.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val toDoViewModel : ToDoViewModel = viewModel()

            ToDoAppGitTheme(toDoViewModel) {
                Navigation(toDoViewModel)
            }
        }
    }
}