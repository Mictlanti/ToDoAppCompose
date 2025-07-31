package com.horizon.todoappgit.views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.horizon.todoappgit.components.BodyLarge
import com.horizon.todoappgit.components.BodyMedium
import com.horizon.todoappgit.components.CardDesign
import com.horizon.todoappgit.components.TopAppBarAddNote
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.events.ToDoEvents
import com.horizon.todoappgit.ui.theme.primaryDarkOther1
import com.horizon.todoappgit.ui.theme.primaryDarkOther2
import com.horizon.todoappgit.ui.theme.primaryLightOther1
import com.horizon.todoappgit.ui.theme.primaryLightOther2
import com.horizon.todoappgit.ui.theme.tertiaryDarkOther1
import com.horizon.todoappgit.ui.theme.tertiaryDarkOther2
import com.horizon.todoappgit.ui.theme.tertiaryLightOther1
import com.horizon.todoappgit.ui.theme.tertiaryLightOther2
import com.horizon.todoappgit.viewmodel.ToDoViewModel

@Composable
fun AddNoteRoute(viewModel: ToDoViewModel, navController: NavController) {

    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 3 })
//    viewModel.addNote()
//    navController.popBackStack()

    Scaffold(
        topBar = {
            TopAppBarAddNote(navController = navController, viewModel = viewModel, pagerState = pagerState)
        },
        modifier = Modifier.fillMaxSize()
    ) { pad ->
        HorizontalAddNote(pad, pagerState, state, viewModel)
    }
}

@Composable
fun HorizontalAddNote(
    pad: PaddingValues,
    pagerState: PagerState,
    state: ToDoState,
    viewModel: ToDoViewModel
) {

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        modifier = Modifier.padding(pad)
    ) { page ->
        when (page) {
            0 -> AddNoteView(state, viewModel)
            1 -> DesignCardView(state, viewModel)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteView(state: ToDoState, viewModel: ToDoViewModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextField(
            value = state.title,
            onValueChange = { viewModel.onEvent(ToDoEvents.TitleTextField(it)) },
            label = { Text(text = "Title") },
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.tertiary,
            ),
            modifier = Modifier
                .fillMaxWidth()

        )
        BodyMedium("Characters: ${state.body.length}", fontSize = 15.sp)
        TextField(
            value = state.body,
            onValueChange = { viewModel.onEvent(ToDoEvents.BodyTextField(it)) },
            label = { Text(text = "Note") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.tertiary,
            ),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun DesignCardView(state: ToDoState, viewModel: ToDoViewModel) {

    val listColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.tertiary,
        if (state.darkTheme) primaryLightOther1 else primaryDarkOther1,
        if (state.darkTheme) tertiaryLightOther1 else tertiaryDarkOther1,
        if (state.darkTheme) primaryLightOther2 else primaryDarkOther2,
        if (state.darkTheme) tertiaryLightOther2 else tertiaryDarkOther2
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(listColors) { index, color ->
                if(state.colorCard == index) {
                    Card(
                        onClick = { viewModel.onEvent(ToDoEvents.SelectedColor(index)) },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = color),
                        modifier = Modifier
                            .size(70.dp)
                            .animateContentSize()
                    ) {}
                }
                else {
                    Card(
                        onClick = { viewModel.onEvent(ToDoEvents.SelectedColor(index)) },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = .5f)),
                        modifier = Modifier
                            .size(50.dp)
                    ) {}
                }
            }
        }
        CardDesign(state.title, state.body, state)
    }
}