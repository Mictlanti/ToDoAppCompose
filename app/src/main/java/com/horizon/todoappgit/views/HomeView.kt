package com.horizon.todoappgit.views

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.horizon.todoappgit.components.BodyLarge
import com.horizon.todoappgit.components.BodyMedium
import com.horizon.todoappgit.components.BoxOptionsOrder
import com.horizon.todoappgit.components.BoxOrderView
import com.horizon.todoappgit.components.CardNotes
import com.horizon.todoappgit.components.FloatingActionBtnHome
import com.horizon.todoappgit.components.HeadLineLarge
import com.horizon.todoappgit.components.IconFloating
import com.horizon.todoappgit.components.ReorderOrSearch
import com.horizon.todoappgit.components.TopAppBarHome
import com.horizon.todoappgit.components.ViewDefaultCards
import com.horizon.todoappgit.components.ViewSmallCards
import com.horizon.todoappgit.components.ViewVerticalGrid
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
    val orderBy = remember { mutableStateOf(false) }

    ModalBtnData(
        showData.value,
        viewModel,
        state,
        idDoc.intValue,
        navController,
        onClickEdit = { navController.navigate(AppScreens.EditNote.widthId(idDoc.intValue)) }) {
        showData.value = false
    }

    ModalBtnOrderBy(orderBy.value, state, viewModel) {
        orderBy.value = false
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
            viewModel,
            openModal = {
                idDoc.intValue = it
                showData.value = true
            },
            orderByClick = { orderBy.value = true }
        )
    }
}

@Composable
private fun LazyHomeView(
    pad: PaddingValues,
    state: ToDoState,
    viewmodel: ToDoViewModel,
    openModal: (Int) -> Unit,
    orderByClick: () -> Unit
) {

    val searchQuery = remember { mutableStateOf("") }
    val onSearch = remember { mutableStateOf(false) }
    val filteredNote = viewmodel.listSearchQuery(searchQuery.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(pad),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ReorderOrSearch(
            onSearch.value,
            searchQuery.value,
            onValueChange = { searchQuery.value = it },
            orderByClick = orderByClick,
            onClickSearch = { onSearch.value = true },
            onDismiss = { onSearch.value = false }
        )
        if (state.sorterView != 2) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    if (state.listHomeWork.isEmpty()) {
                        BodyLarge("Insert ur homework")
                    }
                }
                if (!onSearch.value) {
                    itemsIndexed(state.listHomeWork) { _, s ->
                        when (state.sorterView) {
                            0 -> ViewDefaultCards(s, openModal)
                            1 -> ViewSmallCards(s, openModal)
                        }
                    }
                }
                if (onSearch.value) {
                    itemsIndexed(filteredNote) { _, s ->
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
                }
            }
        }
        if (state.sorterView == 2) {
            ViewVerticalGrid(state, openModal)
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
    onClickEdit: () -> Unit,
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
                        .padding(top = 15.dp, bottom = 0.dp)
                        .padding(start = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box() {}
                        BodyLarge(
                            state.title,
                            color = colorTextCards(state.colorCard)
                        )
                        IconFloating(
                            Icons.Default.Edit,
                            tint = colorTextCards(state.colorCard)
                        ) { onClickEdit() }
                    }
                    BottomSheetDefaults.DragHandle(
                        color = colorTextCards(state.colorCard)
                    )
                }
            },
            containerColor = colorCards(state.colorCard)
        ) {
            when (showView.value) {
                true -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BodyMedium(state.body, color = colorTextCards(state.colorCard))
                    }
                }

                false -> ErrorView(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBtnOrderBy(
    orderBy: Boolean,
    state: ToDoState,
    viewModel: ToDoViewModel,
    onDismiss: () -> Unit
) {

    val optionsToOrder = listOf(
        "Default",
        //"created date",
        "Colors",
        "A - Z",
        "Recently"
    )
    val optionsView = listOf(
        "Default",
        "Small",
        "Grid"
    )

    if (orderBy) {
        ModalBottomSheet(onDismiss) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 0.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
            ) {
                HeadLineLarge(
                    "Order by:",
                    fontSize = 25.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Row {
                    BoxOptionsOrder(
                        optionsToOrder,
                        state,
                        viewModel,
                        modifier = Modifier.weight(1f)
                    )
                    BoxOrderView(optionsView, state, viewModel, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}