package com.horizon.todoappgit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.horizon.todoappgit.components.BodyLarge
import com.horizon.todoappgit.components.BodyMedium
import com.horizon.todoappgit.components.CardNotes
import com.horizon.todoappgit.components.FloatingActionBtnHome
import com.horizon.todoappgit.components.TopAppBarHome
import com.horizon.todoappgit.components.colorCards
import com.horizon.todoappgit.components.colorTextCards
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.navigation.AppScreens
import com.horizon.todoappgit.viewmodel.ToDoViewModel

@Composable
fun HomeViewRoute(viewModel: ToDoViewModel, navController: NavController) {

    val state by viewModel.state.collectAsState()
    val showData = remember { mutableStateOf(false) }
    val idDoc = remember { mutableIntStateOf(0) }

    ModalBtnData(showData.value, viewModel, state, idDoc.intValue, navController) {
        showData.value = false
    }

    Scaffold(
        topBar = { TopAppBarHome(navController = navController, viewModel = viewModel) },
        floatingActionButton = { FloatingActionBtnHome(navController) },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { pad ->
        LazyHomeView(
            pad,
            state,
            onClickCad = { index -> navController.navigate(AppScreens.EditNote.widthId(index)) }
        ) {
            idDoc.intValue = it
            showData.value = true
        }
    }
}

@Composable
private fun LazyHomeView(
    pad: PaddingValues,
    state: ToDoState,
    onClickCad: (Int) -> Unit,
    openModal: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(pad)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(state.listHomeWork) { _, s ->
            if (state.listHomeWork.isEmpty()) {
                BodyLarge("Insert ur homework")
            } else {
                CardNotes(
                    s.title,
                    s.body,
                    s,
                    onClickEdit = { onClickCad(s.id) }) { openModal(s.id) }
                Spacer(Modifier.height(20.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .height(2.dp)
                        .background(Color.Gray.copy(alpha = .2f))
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModalBtnData(
    showData: Boolean,
    viewModel: ToDoViewModel,
    state: ToDoState,
    idDoc: Int,
    navController: NavController,
    onDismiss: () -> Unit
) {

    val showView = remember { mutableStateOf(false) }

    LaunchedEffect(idDoc) {
        val showValues = viewModel.getNoteById(idDoc)
        showView.value = showValues
    }

    if (showData && showView.value) {
        ModalBottomSheet(
            onDismiss,
            dragHandle = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (showView.value) {
                        BodyLarge(state.title)
                    }
                    BottomSheetDefaults.DragHandle()
                }
            },
            containerColor = colorCards(state.colorCard)
        ) {
            when(showView.value) {
                true -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BodyMedium(state.body)
                    }
                }
                false -> ErrorView(navController)
            }
        }
    }
}