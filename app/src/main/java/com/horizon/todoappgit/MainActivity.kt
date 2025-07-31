package com.horizon.todoappgit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.horizon.todoappgit.data.datastore.DataAppStore
import com.horizon.todoappgit.navigation.Navigation
import com.horizon.todoappgit.ui.theme.ToDoAppGitTheme
import com.horizon.todoappgit.viewmodel.ToDoViewModel
import com.horizon.todoappgit.views.HomeViewRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val toDoViewModel : ToDoViewModel = viewModel()
            val dataPref = DataAppStore

            ToDoAppGitTheme(toDoViewModel) {
                Navigation(toDoViewModel)
            }
        }
    }
}