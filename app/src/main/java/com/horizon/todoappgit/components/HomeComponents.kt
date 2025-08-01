package com.horizon.todoappgit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.horizon.todoappgit.data.HomeworkState
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.events.ToDoEvents
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
    FloatingActionButton(
        onClick = { navController.navigate(AppScreens.AddNoteView.route) },
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        IconFloating(Icons.Default.Add) {
            navController.navigate(AppScreens.AddNoteView.route)
        }
    }
}

@Composable
fun IconFloating(
    imageVector: ImageVector,
    size: Dp = 30.dp,
    tint: Color = MaterialTheme.colorScheme.onTertiary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(onClick = onClick, modifier) {
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
    fontSizeTitle: TextUnit = 25.sp,
    fontSizeBody: TextUnit = 15.sp,
    openModal: () -> Unit
) {

    val lengthBody = if (body.length > 28) body.take(28) + "..." else body

    ElevatedCard(
        onClick = { openModal() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = colorCards(homeWorkState.color))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            BodyLarge(title, color = colorTextCards(homeWorkState.color), fontSize = fontSizeTitle)
            BodyMedium(lengthBody, color = colorTextCards(homeWorkState.color), fontSize = fontSizeBody)
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

@Composable
fun ReorderOrSearch(
    onSearch: Boolean,
    searchQuery: String,
    onValueChange: (String) -> Unit,
    orderByClick: () -> Unit,
    onClickSearch: () -> Unit,
    onDismiss: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (!onSearch) {
            IconFloating(
                Icons.Default.Reorder,
                tint = MaterialTheme.colorScheme.onBackground
            ) { orderByClick() }
        } else {
            TextField(
                searchQuery,
                onValueChange = { onValueChange(it) },
                modifier = Modifier.weight(3f),
                leadingIcon = { Icon(Icons.Default.Search, "Search") },
                trailingIcon = {
                    IconButton(
                        onClick = { onDismiss() }
                    ) {
                        Icon(Icons.Default.Close, "Close")
                    }
                },
                label = { Text("Search Note") }
            )
        }
        if (!onSearch) {
            IconFloating(
                Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onBackground
            ) { onClickSearch() }
        }
    }
}

@Composable
fun BoxOptionsOrder(
    optionsToOrder: List<String>,
    state: ToDoState,
    viewModel: ToDoViewModel,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxWidth()
    ) {
        optionsToOrder.forEachIndexed { index, s ->
            BodyMedium(
                s,
                fontSize = 13.sp,
                color = if (index == state.orderBy) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.clickable { viewModel.sortedBy(index) }
            )
        }
    }
}

@Composable
fun BoxOrderView(
    optionsView: List<String>,
    state: ToDoState,
    viewModel: ToDoViewModel,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier.fillMaxWidth()
    ) {
        optionsView.forEachIndexed { index, s ->
            BodyMedium(
                s,
                fontSize = 13.sp,
                color = if (index == state.orderBy) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.clickable { viewModel.onEvent(ToDoEvents.SortedView(index)) }
            )
        }
    }

}

@Composable
fun ViewDefaultCards(s: HomeworkState, openModal: (Int) -> Unit) {
    CardNotes(
        s.title,
        s.body,
        s
    ) { openModal(s.id) }
    Spacer(Modifier.height(20.dp))
    HorizontalDivider(
        modifier = Modifier
            .height(2.dp)
            .background(Color.Gray.copy(alpha = .2f))
            .padding(horizontal = 12.dp)
    )
}

@Composable
fun ViewSmallCards(s: HomeworkState, openModal: (Int) -> Unit) {
    CardNotes(
        s.title,
        s.body,
        s,
        fontSizeTitle = 20.sp,
        fontSizeBody = 10.sp
    ) { openModal(s.id) }
    Spacer(Modifier.height(20.dp))
    HorizontalDivider(
        modifier = Modifier
            .height(2.dp)
            .background(Color.Gray.copy(alpha = .2f))
            .padding(horizontal = 12.dp)
    )
}

@Composable
fun ViewVerticalGrid(state: ToDoState, openModal: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(state.listHomeWork) { index, s ->
            CardVerticalGrid(s, openModal)
        }
    }
}

@Composable
private fun CardVerticalGrid(s: HomeworkState, openModal: (Int) -> Unit) {

    val lengthTitle = if(s.title.length > 8) s.title.take(8) + "..." else s.title
    val lengthBody = if (s.body.length > 28) s.body.take(28) + "..." else s.body

    ElevatedCard(
        onClick = { openModal(s.id) },
        modifier = Modifier
            .padding(12.dp)
            .padding()
            .wrapContentSize(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = colorCards(s.color))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            BodyLarge(lengthTitle, color = colorTextCards(s.color), fontSize = 18.sp)
            BodyMedium(lengthBody, color = colorTextCards(s.color), fontSize = 13.sp)
        }
    }
}