package com.example.messenger.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.messenger.R
import com.example.messenger.model.ChatRoom
import com.example.messenger.ui.common_ui.MessengerAppBar
import com.example.messenger.ui.theme.MessagerTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    goSettingScreen: () -> Unit = {},
    goLogInScreen: () -> Unit,
    goChatRoomScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    Log.d("HomeScreen", uiState.currentUser.toString())

    LaunchedEffect(key1 = uiState.currentUser) {
        if (uiState.currentUser == null) {
            goLogInScreen()
            Log.d("HomeScreen", "hello")
            viewModel.resetState()
            return@LaunchedEffect
        }
    }

    LaunchedEffect(key1 = uiState.signInError) {
        uiState.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(
        topBar = {
            MessengerAppBar(
                label = Firebase.auth.currentUser?.displayName ?: "Anonymous",
                canNavigateBack = true,
                action = {
                    IconButton(onClick = goSettingScreen) {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                    }
                },

                )
        }
    ) {
        ListChatRooms(
            uiState = uiState, modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            goChatRoomScreen = goChatRoomScreen
        )
    }
}

@Composable
fun ListChatRooms(uiState: HomeUiState, goChatRoomScreen: () -> Unit, modifier: Modifier = Modifier) {
    val listChatRoom = uiState.chatRooms
    if (listChatRoom.isNotEmpty()) {
        LazyColumn(modifier = modifier) {
            items(listChatRoom) { chatroom ->
                ChatRoomCard(
                    chatroom = chatroom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { goChatRoomScreen() })
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.empty_list),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun ChatRoomCard(chatroom: ChatRoom, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(chatroom.chatRoomBackground)
                    .crossfade(true)
                    .placeholder(R.drawable.dog)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
//            if (friend.online) {
                Image(
                    painter = painterResource(id = R.drawable.online),
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .border(color = Color.White, width = 1.dp, shape = CircleShape)
                        .align(Alignment.BottomEnd)
                )
//            }
        }

        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Hoang Manh Long", style = MaterialTheme.typography.titleMedium)
            Text(text = "hello")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendlyCardPreview() {
    MessagerTheme {
//        FriendCard(Friend(1, "Long", "", true))
    }
}
