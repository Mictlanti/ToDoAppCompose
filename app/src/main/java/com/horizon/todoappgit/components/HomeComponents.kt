package com.horizon.todoappgit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.horizon.todoappgit.data.ToDoState
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
    onClickActionBtn : () -> Unit = {}
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
fun CardNotes(title: String, body: String, state: ToDoState, onOpenModal: () -> Unit) {

    val lengthBody = if (body.length > 20) body.take(20) + " ..." else body
    val showOptions = remember { mutableStateOf(false) }

    ElevatedCard(
        onClick = { showOptions.value = false },
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = colorCards(state))
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
                BodyLarge(title, color = colorTextCards(state))
                IconFloating(
                    Icons.Default.Edit,
                    tint = colorTextCards(state)
                ) { onOpenModal() }
            }
            BodyMedium(lengthBody, color = colorTextCards(state))
        }
    }
}

fun colorCards(state: ToDoState): Color {
    return if (state.darkTheme) {
        when (state.colorCard) {
            0 -> primaryLight
            1 -> tertiaryLight
            2 -> primaryLightOther1
            3 -> tertiaryLightOther1
            4 -> primaryLightOther2
            5 -> tertiaryLightOther2
            else -> primaryLight
        }
    } else {
        when (state.colorCard) {
            0 -> primaryDark
            1 -> tertiaryDark
            2 -> primaryDarkOther1
            3 -> tertiaryDarkOther1
            4 -> primaryDarkOther2
            5 -> tertiaryDarkOther2
            else -> primaryDark
        }
    }
}

fun colorTextCards(state: ToDoState): Color {
    return if (state.darkTheme) {
        when (state.colorCard) {
            0 -> onPrimaryLight
            1 -> onTertiaryLight
            2 -> onPrimaryLightOther1
            3 -> onTertiaryLightOther1
            4 -> onPrimaryLightOther2
            5 -> onTertiaryLightOther2
            else -> onPrimaryLight
        }
    } else {
        when (state.colorCard) {
            0 -> onPrimaryDark
            1 -> onTertiaryDark
            2 -> onPrimaryDarkOther1
            3 -> onTertiaryDarkOther1
            4 -> onPrimaryDarkOther2
            5 -> onTertiaryDarkOther2
            else -> primaryDark
        }
    }
}