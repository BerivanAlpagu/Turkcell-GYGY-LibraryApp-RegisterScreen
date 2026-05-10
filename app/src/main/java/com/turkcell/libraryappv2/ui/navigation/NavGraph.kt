package com.turkcell.libraryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.libraryappv2.ui.screen.HomeScreen
import com.turkcell.libraryappv2.ui.screen.LoginScreen
import com.turkcell.libraryappv2.ui.screen.RegisterScreen
import com.turkcell.libraryappv2.ui.viewmodel.AuthViewModel


@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    //her bir screen için viewmodel yazmaktan kurulmak için
    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Login.route)
    {
        composable(Screen.Login.route) { LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route) },
                onLoginSuccess = {role -> navController.navigate(Screen.Homepage.route) {
                    popUpTo(Screen.Login.route) {inclusive=true}
                    // Yığın yalnızca verilen URL ile kalacaktı (false)
                    }
                },
                authViewModel
            ) }
        composable(Screen.Register.route) { RegisterScreen(
            onNavigateToLogin = { navController.navigate(Screen.Login.route) },
            authViewModel
        ) }
        composable(Screen.Homepage.route){
            HomeScreen(authViewModel)
        }
    }
}