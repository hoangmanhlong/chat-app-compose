package com.example.messenger.ui.sign_in

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.messenger.R
import com.example.messenger.ui.common_ui.MessengerAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    goHomeScreen: () -> Unit,
    goSignInWithEmailScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    Log.d("SignInWithEmailScreen", "LoginScreen created")

    val coroutineScope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                coroutineScope.launch {
                    val signInResult = viewModel.signInWithIntent(result.data ?: return@launch)
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )

    LaunchedEffect(key1 = uiState.isSignInSuccessful) {
        if (uiState.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()
        }
        if (uiState.isSignInSuccessful) {
            goHomeScreen()
            return@LaunchedEffect
        }
    }

    Scaffold(
        topBar = {
            MessengerAppBar(label = stringResource(id = R.string.app_name), canNavigateBack = false)
        }
    ) {
        LoginBody(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            onLogin = {
                coroutineScope.launch {
                    val signInIntentSender = viewModel.signIn()
                    launcher.launch(
                        IntentSenderRequest
                            .Builder(signInIntentSender ?: return@launch)
                            .build()
                    )
                }
            },
            goSignInWithEmailScreen = goSignInWithEmailScreen,
        )
    }
}

@Composable
fun LoginBody(
    onLogin: () -> Unit,
    goSignInWithEmailScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 32.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = goSignInWithEmailScreen, modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(text = stringResource(id = R.string.sign_in_email))
                }

            }
            OutlinedButton(
                onClick = onLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.flat_color_icons_google),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(text = stringResource(id = R.string.sign_in_google))
                }
            }
        }
    }
}
