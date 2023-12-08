package com.example.messenger.ui.sign_in_with_email

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.messenger.R
import com.example.messenger.ui.MessengerAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInWithEmailScreen(
    backLogInScreen: () -> Unit,
    goSignUpWithEmailScreen: () -> Unit,
    viewModel: SignInWithEmailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Log.d("SignInWithEmailScreen", "SignInWithEmailScreen created")
    Scaffold(
        topBar = {
            MessengerAppBar(
                label = stringResource(id = R.string.sign_in_email),
                canNavigateBack = true,
                onNavigateUp = {
                    IconButton(onClick =  backLogInScreen) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        SignInWithEmailBody(
            viewModel = viewModel,
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            onLogin = {},
            goSignUpWithEmailScreen = goSignUpWithEmailScreen,
            uiState = uiState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInWithEmailBody(
    onLogin: () -> Unit,
    uiState: SignInWithEmailUiState,
    goSignUpWithEmailScreen: () -> Unit,
    viewModel: SignInWithEmailViewModel,
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
            OutlinedTextField(
                label = { Text(stringResource(id = R.string.email)) },
                value = uiState.email,
                onValueChange = { viewModel.updateUsername(it) },
                isError = false,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (uiState.email.isNotBlank()) {
                        IconButton(onClick = { viewModel.clearText(1) }) {
                            Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                label = { Text(stringResource(id = R.string.password)) },
                value = uiState.password,
                onValueChange = { viewModel.updatePassword(it) },
                isError = false,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    if (uiState.password.isNotBlank()) {
                        IconButton(onClick = { viewModel.clearText(2) }) {
                            Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions()
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onLogin,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = viewModel.inputIsTrue()
                ) {
                    Text(text = stringResource(id = R.string.sign_in).uppercase())
                }

                OutlinedButton(onClick = goSignUpWithEmailScreen, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.sign_up_email))
                }
            }
        }
    }
}
