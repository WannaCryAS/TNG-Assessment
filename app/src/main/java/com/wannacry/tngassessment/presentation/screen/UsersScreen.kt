package com.wannacry.tngassessment.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wannacry.tngassessment.config.Constants.AVATAR_URL
import com.wannacry.tngassessment.config.Constants.EXTEND_URL_BACKGROUND
import com.wannacry.tngassessment.config.Constants.EXTEND_URL_NAME
import com.wannacry.tngassessment.config.Constants.RANDOM
import com.wannacry.tngassessment.presentation.UiState
import com.wannacry.tngassessment.presentation.screen.component.UserItem
import com.wannacry.tngassessment.presentation.viewmodel.UsersViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UsersScreen(viewModel: UsersViewModel) {
    val state by viewModel.state.collectAsState()
    var isPullRefreshing by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        listState.scrollToItem(0)
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isPullRefreshing,
        onRefresh = {
            isPullRefreshing = true
            viewModel.getUsers(
                onComplete = { isPullRefreshing = false },
                isRefreshing = true
            )
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "List of User",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                actions = {

                    IconButton(
                        onClick = { viewModel.sortByName(true) },
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Icon(Icons.Filled.ArrowDownward, contentDescription = "Sort a-z")
                    }
                    IconButton(
                        onClick = { viewModel.sortByName(false) },
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Icon(Icons.Filled.ArrowUpward, contentDescription = "Sort z-a")
                    }
                },
                modifier = Modifier.statusBarsPadding(),
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {
            when (state) {
                is UiState.Loading -> {
                    if (!isPullRefreshing) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = (state as UiState.Error).errorMessage,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.getUsers() }) {
                                Text("Try Again")
                            }
                        }
                    }
                }

                is UiState.Success -> {
                    val users = (state as UiState.Success).users
                    if (users.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No users found", textAlign = TextAlign.Center)
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
                            items(users, key = { it.id }) { user ->
                                val avatarName = user.username.takeIf { !it.isNullOrBlank() } ?: user.name
                                val avatarUrl = "$AVATAR_URL$EXTEND_URL_NAME$avatarName&$EXTEND_URL_BACKGROUND$RANDOM"

                                UserItem(
                                    data = user,
                                    context = context,
                                    avatarUrl = avatarUrl,
                                )
                            }
                        }
                    }
                }
            }

            if (isPullRefreshing) {
                PullRefreshIndicator(
                    refreshing = true,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}
