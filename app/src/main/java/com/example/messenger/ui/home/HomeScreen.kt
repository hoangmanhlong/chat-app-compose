package com.example.messenger.ui.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.messenger.R
import com.example.messenger.model.Friend
import com.example.messenger.service.model.SignInResult
import com.example.messenger.ui.MessengerAppBar
import com.example.messenger.ui.theme.MessagerTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    goSettingScreen: () -> Unit = {},
    goLogInScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    Log.d("HomeScreen", uiState.currentUser.toString())
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = uiState.currentUser) {
        if(uiState.currentUser == null) {
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
                label = stringResource(
                    id = R.string.hi,
                    (Firebase.auth.currentUser?.displayName) ?: "Anonymous"
                ),
                canNavigateBack = true,
                action = {
                    IconButton(onClick = goSettingScreen) {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                    }
                }
            )
        }
    ) {
        ListFriend(
            uiState = uiState, modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        )
    }
}

@Composable
fun ListFriend(
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(uiState.friends) { friend ->
            FriendCard(friend = friend)
        }
    }
}

@Composable
fun FriendCard(friend: Friend) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
//        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
//            .data(friend.avatar)
//            .crossfade(true)
//            .build(),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.clip(CircleShape)
//        )
        Box {
            Image(
                painter = painterResource(id = R.drawable.dog),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)

            )
            if (friend.online) {
                Image(
                    painter = painterResource(id = R.drawable.online),
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .border(color = Color.White, width = 1.dp, shape = CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }
        }

        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = friend.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "hello")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendlyCardPreview() {
    MessagerTheme {
        FriendCard(Friend(1, "Long", "", true))
    }
}

