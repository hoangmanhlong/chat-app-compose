package com.example.messenger.ui.sign_up_with_email

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.messenger.R
import com.example.messenger.ui.common_ui.MessengerAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    backSignInWithEmailScreen: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MessengerAppBar(
                label = stringResource(id = R.string.sign_up_email),
                canNavigateBack = true,
                onNavigateUp = {
                    IconButton(onClick = backSignInWithEmailScreen) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        SignUpBody(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            viewModel = viewModel,
            onSignUp = {
                coroutineScope.launch { viewModel.onSignUp() }
                backSignInWithEmailScreen()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpBody(viewModel: SignUpViewModel, modifier: Modifier = Modifier, onSignUp: () -> Unit) {
    val uiState = viewModel.uiState

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
                    imeAction = ImeAction.Next
                )

            )

            OutlinedTextField(
                label = { Text(stringResource(id = R.string.confirm)) },
                value = uiState.confirmPassword,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                isError = false,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    if (uiState.confirmPassword.isNotBlank()) {
                        IconButton(onClick = { viewModel.clearText(3) }) {
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

            Button(
                onClick = onSignUp,
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.check()
            ) {
                Text(text = stringResource(id = R.string.register).uppercase())
            }
        }
    }
}