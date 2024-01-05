package com.example.messenger.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.messenger.ui.chat.ChatRoomScreen
import com.example.messenger.ui.home.HomeScreen
import com.example.messenger.ui.setting.SettingScreen
import com.example.messenger.ui.sign_in.LoginScreen
import com.example.messenger.ui.sign_in_with_email.SignInWithEmailScreen
import com.example.messenger.ui.sign_up_with_email.SignUpScreen
import com.example.messenger.ui.splash.SplashScreen

@Composable
fun MessengerNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = modifier
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen {
                navController.navigate(Screen.HomeScreen.route)
            }
        }
        composable(route = Screen.SignInScreen.route) {
            LoginScreen(
                goHomeScreen = { navController.navigate(Screen.HomeScreen.route) },
                goSignInWithEmailScreen = { navController.navigate(Screen.SignInWithEmailScreen.route) }
            )
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                goSettingScreen = { navController.navigate(Screen.SettingScreen.route) },
                goLogInScreen = {
                    navController.popBackStack()
                    navController.navigate(Screen.SignInScreen.route)
                },
                goChatRoomScreen = { navController.navigate(Screen.ChatRoomScreen.route) }
            )
        }

        composable(route = Screen.SettingScreen.route) {
            SettingScreen(
                backHomeScreen = { navController.navigateUp() },
                backLogInScreen = {
                    navController.popBackStack()
                    navController.navigate(Screen.SignInScreen.route)
                }
            )
        }

        composable(route = Screen.SignInWithEmailScreen.route) {
            SignInWithEmailScreen(
                backLogInScreen = { navController.navigateUp() },
                goSignUpWithEmailScreen = { navController.navigate(Screen.SignUpWithEmailScreen.route) }
            )
        }

        composable(route = Screen.SignUpWithEmailScreen.route) {
            SignUpScreen(
                backSignInWithEmailScreen = { navController.navigateUp() },
            )
        }

        composable(route = Screen.ChatRoomScreen.route) {
            ChatRoomScreen(onNavigateUp = {navController.navigateUp()})
        }
    }
}