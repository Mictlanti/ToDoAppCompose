package com.horizon.todoappgit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.horizon.todoappgit.components.BodyLarge
import com.horizon.todoappgit.components.BodyTextField
import com.horizon.todoappgit.components.CardNotes
import com.horizon.todoappgit.components.FloatingActionBtnHome
import com.horizon.todoappgit.components.IconFloating
import com.horizon.todoappgit.components.TitleTextField
import com.horizon.todoappgit.components.TopAppBarHome
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.navigation.AppScreens
import com.horizon.todoappgit.viewmodel.ToDoViewModel

@Composable
fun HomeViewRoute(viewModel: ToDoViewModel, navController: NavController) {

    val state by viewModel.state.collectAsState()
    val openModal = remember { mutableStateOf(false) }
    val idDoc = rememberSaveable { mutableIntStateOf(0) }

    ModalBtnSheet(
        openModal.value,
        idDoc.intValue,
        state,
        viewModel
    ) { openModal.value = false }

    Scaffold(
        topBar = { TopAppBarHome(navController = navController, viewModel = viewModel) },
        floatingActionButton = { FloatingActionBtnHome(navController) },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { pad ->
        LazyHomeView(pad, state) { index ->
            navController.navigate(AppScreens.EditNote.widthId(index))
        }
    }
}

@Composable
fun LazyHomeView(
    pad: PaddingValues,
    state: ToDoState,
    onClickCad: (Int) -> Unit
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
                CardNotes(s.title, s.body, state) { onClickCad(s.id) }
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
fun ModalBtnSheet(
    openModal: Boolean,
    idDoc: Int,
    state: ToDoState,
    viewModel: ToDoViewModel,
    onDismiss: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getNoteById(idDoc)
    }

    if (openModal) {
        ModalBottomSheet(
            onDismiss,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            dragHandle = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconFloating(
                            Icons.Default.Edit,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        IconFloating(
                            Icons.Default.Delete,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        ) { viewModel.deleteHomework() }
                    }
                    BottomSheetDefaults.DragHandle()
                }
            }
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                TitleTextField(state, viewModel)
                Spacer(Modifier.height(10.dp))
                BodyTextField(state, viewModel)
            }
        }
    }
}