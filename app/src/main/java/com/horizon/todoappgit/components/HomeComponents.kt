package com.horizon.todoappgit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.horizon.todoappgit.data.HomeworkState
import com.horizon.todoappgit.navigation.AppScreens
import com.horizon.todoappgit.ui.theme.onPrimaryDark
import com.horizon.todoappgit.ui.theme.onPrimaryDarkOther1
import com.horizon.todoappgit.ui.theme.onPrimaryDarkOther2
import com.horizon.todoappgit.ui.theme.onPrimaryLight
import com.horizon.todoappgit.ui.theme.onPrimaryLightOther1
import com.horizon.todoappgit.ui.theme.onPrimaryLightOther2
import com.horizon.todoappgit.ui.theme.onTertiaryDark
import com.horizon.todoappgit.ui.theme.onTertiaryDarkOther1
import com.horizon.todoappgit.ui.theme.onTertiaryDarkOther2
import com.horizon.todoappgit.ui.theme.onTertiaryLight
import com.horizon.todoappgit.ui.theme.onTertiaryLightOther1
import com.horizon.todoappgit.ui.theme.onTertiaryLightOther2
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
fun TopAppBarHome(
    text: String = "To Do App",
    navController: NavController,
    viewModel: ToDoViewModel,
    navIcon: Boolean = false,
    onClickActionBtn: () -> Unit = {}
) {

    val state by viewModel.state.collectAsState()

    CenterAlignedTopAppBar(
        title = {
            HeadLineLarge(text = text)
        },
        navigationIcon = {
            if (navIcon) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBackIos,
                        "Arrow Back"
                    )
                }
            }
        },
        actions = {
            if (navIcon) {
                IconButton(onClick = { onClickActionBtn() }) {
                    Icon(
                        Icons.Default.Done,
                        "Done"
                    )
                }
            } else {
                AnimatedVisibility(
                    !state.darkTheme,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    Icon(
                        Icons.Default.DarkMode,
                        "DarkMode"
                    )
                }
                AnimatedVisibility(
                    state.darkTheme,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    Icon(
                        Icons.Default.LightMode,
                        "Light Mode"
                    )
                }
                Spacer(Modifier.width(5.dp))
                Switch(
                    checked = state.darkTheme,
                    onCheckedChange = { viewModel.changeThemeMode(it) }
                )
            }
        }
    )
}

@Composable
fun FloatingActionBtnHome(navController: NavController) {

    val action = remember { mutableStateOf(false) }

    AnimatedVisibility(
        action.value,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        FloatingActionButton(
            onClick = { },
            containerColor = MaterialTheme.colorScheme.tertiary
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                IconFloating(Icons.Default.Remove) { action.value = false }
                IconFloating(Icons.AutoMirrored.Filled.NoteAdd) {
                    navController.navigate(AppScreens.AddNoteView.route)
                }
                IconFloating(Icons.Default.NoteAlt) { }
            }
        }
    }
    AnimatedVisibility(
        !action.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FloatingActionButton(
            onClick = { action.value = true },
            containerColor = MaterialTheme.colorScheme.tertiary
        ) {
            IconFloating(Icons.Default.Add) { action.value = true }
        }
    }
}

@Composable
fun IconFloating(
    imageVector: ImageVector,
    size: Dp = 30.dp,
    tint: Color = MaterialTheme.colorScheme.onTertiary,
    onClick: () -> Unit = {}
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector,
            "Add Note",
            modifier = Modifier.size(size),
            tint = tint
        )
    }
}

@Composable
fun CardNotes(
    title: String,
    body: String,
    homeWorkState: HomeworkState,
    onClickEdit: () -> Unit,
    openModal: () -> Unit
) {

    val lengthBody = if (body.length > 28) body.take(28) + "..." else body

    ElevatedCard(
        onClick = { openModal() },
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = colorCards(homeWorkState.color))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                BodyLarge(title, color = colorTextCards(homeWorkState.color))
                IconFloating(
                    Icons.Default.Edit,
                    tint = colorTextCards(homeWorkState.color)
                ) { onClickEdit() }
            }
            BodyMedium(lengthBody, color = colorTextCards(homeWorkState.color))
        }
    }
}

@Composable
fun colorCards(value: Int): Color {
    return when (value) {
        0 -> MaterialTheme.colorScheme.background
        1 -> primaryLight
        2 -> tertiaryLight
        3 -> primaryLightOther1
        4 -> tertiaryLightOther1
        5 -> primaryLightOther2
        6 -> tertiaryLightOther2
        7 -> primaryDark
        8 -> tertiaryDark
        9 -> primaryDarkOther1
        10 -> tertiaryDarkOther1
        11 -> primaryDarkOther2
        12 -> tertiaryDarkOther2
        else -> primaryDark
    }
}

@Composable
fun colorTextCards(value: Int): Color {
    return when (value) {
        0 -> MaterialTheme.colorScheme.onBackground
        1 -> onPrimaryLight
        2 -> onTertiaryLight
        3 -> onPrimaryLightOther1
        4 -> onTertiaryLightOther1
        5 -> onPrimaryLightOther2
        6 -> onTertiaryLightOther2
        7 -> onPrimaryDark
        8 -> onTertiaryDark
        9 -> onPrimaryDarkOther1
        10 -> onTertiaryDarkOther1
        11 -> onPrimaryDarkOther2
        12 -> onTertiaryDarkOther2
        else -> onPrimaryDark
    }
}