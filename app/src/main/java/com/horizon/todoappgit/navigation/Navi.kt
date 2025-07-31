package com.horizon.todoappgit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.horizon.todoappgit.viewmodel.ToDoViewModel
import com.horizon.todoappgit.views.AddNoteRoute
import com.horizon.todoappgit.views.EditNoteRoute
import com.horizon.todoappgit.views.HomeViewRoute

@Composable
fun Navigation(viewModel: ToDoViewModel) {

    val navController = rememberNavController()

    NavHost(navController, AppScreens.HomeView.route) {
        composable(AppScreens.HomeView.route) {
            HomeViewRoute(viewModel, navController)
        }

        composable(AppScreens.AddNoteView.route) {
            AddNoteRoute(viewModel, navController)
        }

        composable(
            "${AppScreens.EditNote.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: 0
            EditNoteRoute(navController, viewModel, id)
        }

    }

}