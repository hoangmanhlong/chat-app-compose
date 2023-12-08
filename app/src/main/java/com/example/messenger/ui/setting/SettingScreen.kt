package com.example.messenger.ui.setting

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.messenger.R
import com.example.messenger.model.User
import com.example.messenger.service.model.UserData
import com.example.messenger.ui.ConfirmDialog
import com.example.messenger.ui.MessengerAppBar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    backHomeScreen: () -> Unit,
    backLogInScreen: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MessengerAppBar(
                label = stringResource(id = R.string.setting),
                canNavigateBack = true,
                onNavigateUp = {
                    IconButton(onClick = backHomeScreen) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                })
        }
    ) {
        SettingBody(
            viewModel = viewModel,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            logout = {
                coroutineScope.launch {
                    viewModel.logout()
                    backLogInScreen()
                }
            },
            onDelete = {
                coroutineScope.launch { viewModel.deleteAccount() }
                backLogInScreen()
            }

        )
    }
}

@Composable
fun SettingBody(
    viewModel: SettingViewModel,
    logout: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    var delete by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UserProfile(
                userData = uiState.currentUser ?: UserData(),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.dark_model),
                    style = MaterialTheme.typography.titleMedium
                )
                Switch(
                    checked = uiState.onThemeChange,
                    onCheckedChange = { viewModel.updateTheme(it) })
            }

            Text(
                text = stringResource(id = R.string.delete_account),
                color = Color.Red,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable {
                    delete = true
                }
            )
        }

        Button(onClick = logout, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.logout))
        }

        if (delete) {
            ConfirmDialog(
                onDismiss = { delete = false },
                onDelete = onDelete
            )
        }
    }
}

@Composable
fun UserProfile(userData: UserData, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userData.profilePictureUrl ?: R.drawable.ic_anonymous)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(2f)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
            )
            Spacer(modifier = Modifier.weight(1f))
        }


        Text(text = userData.username ?: "Anonymous", style = MaterialTheme.typography.titleLarge)
    }
}