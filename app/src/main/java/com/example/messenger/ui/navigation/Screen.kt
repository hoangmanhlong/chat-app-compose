package com.example.messenger.ui.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash")
    object SignInScreen: Screen("sign_in")
    object HomeScreen: Screen("home")
    object SettingScreen : Screen("setting")
    object SignUpWithEmailScreen: Screen("sign_up_email")
    object SignInWithEmailScreen : Screen("sign_in_email")
    object ChatRoomScreen : Screen("chat_room")
}
