package com.example.messenger.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.messenger.R
import com.example.messenger.model.Message
import com.example.messenger.ui.common_ui.MessengerAppBar
import com.example.messenger.ui.theme.md_theme_dark_background
import com.example.messenger.ui.theme.md_theme_light_background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    onNavigateUp: () -> Unit,
    viewModel: ChatRoomViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            MessengerAppBar(
                label = "Hoàng Mạnh Long",
                canNavigateBack = true,
                onNavigateUp = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                extraAction = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null)
                    }
                },
                action = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)

    ) {

        ChatRoomBody(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            viewModel = viewModel,
            uiState = uiState
        )
    }
}

@Composable
fun ChatRoomBody(
    viewModel: ChatRoomViewModel,
    uiState: ChatRoomStatus,
    modifier: Modifier = Modifier
) {
    val messages: List<Message> = emptyList();
    Column(modifier = modifier) {
        ListMessage(
            messages = messages, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
        )
        ChatBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            viewModel = viewModel,
            uiState = uiState
        )
    }
}

@Composable
fun ChatBar(viewModel: ChatRoomViewModel, uiState: ChatRoomStatus, modifier: Modifier = Modifier) {
    val message = uiState.message
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Photo, contentDescription = null)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.CenterStart
        ) {
            if (message.isBlank()) {
                Text(text = "Message", color = Color.Gray)
            }

            BasicTextField(
                value = uiState.message,
                onValueChange = { viewModel.updateMessage(it) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        IconButton(
            onClick = { /*TODO*/ },
            enabled = message.isNotBlank(),
            modifier = Modifier.background(
                if (isSystemInDarkTheme()) md_theme_light_background else md_theme_dark_background,
                CircleShape
            )
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) md_theme_dark_background else md_theme_light_background,
            )
        }
    }
}

@Composable
fun ListMessage(modifier: Modifier = Modifier, messages: List<Message>) {
    LazyColumn(modifier = modifier) {
        items(messages, key = { message -> { message.messageId } }) { message ->
            MessageCard(
                message = message,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }
    }
}

@Composable
fun MessageCard(
    message: Message, modifier: Modifier = Modifier
) {
//    Row(
//        modifier = modifier,
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(message.photoUrl ?: R.drawable.ic_anonymous)
//                .crossfade(true)
//                .build(),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .size(50.dp)
//                .clip(CircleShape)
//        )
//        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
//            Text(
//                text = message.sender?.username ?: stringResource(id = R.string.anonymous),
//                color = Color.Gray
//            )
//            Card(shape = MaterialTheme.shapes.small) {
//                Column(modifier = Modifier.padding(8.dp)) {
//                    Text(text = message.text ?: "")
//                }
//            }
//        }
//    }
}
