package com.turkcell.libraryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.libraryappv2.ui.screen.AdminScreen
import com.turkcell.libraryappv2.ui.screen.BorrowingsScreen
import com.turkcell.libraryappv2.ui.screen.HomeScreen
import com.turkcell.libraryappv2.ui.screen.LoginScreen
import com.turkcell.libraryappv2.ui.screen.RegisterScreen
import com.turkcell.libraryappv2.ui.screen.SplashScreen
import com.turkcell.libraryappv2.ui.viewmodel.AdminViewModel
import com.turkcell.libraryappv2.ui.viewmodel.AuthViewModel
import com.turkcell.libraryappv2.ui.viewmodel.BookViewModel
import com.turkcell.libraryappv2.ui.viewmodel.BorrowViewModel


@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val authViewModel: AuthViewModel = viewModel()
    val bookViewModel: BookViewModel = viewModel()
    val borrowViewModel: BorrowViewModel = viewModel()
    val adminViewModel: AdminViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Splash.route)
    {
        composable(Screen.Splash.route) {
            SplashScreen(authViewModel,
                onAuthenticated = { role ->
                    val destination = if (role == "admin") Screen.AdminPanel.route else Screen.Homepage.route
                    navController.navigate(destination) {
                        popUpTo(Screen.Splash.route) {inclusive=true}
                    }
                },
                onUnauthenticated = {
                    navController.navigate(Screen.Login.route)
                    {
                        popUpTo(Screen.Splash.route) {inclusive=true}
                    }
                })
        }
        composable(Screen.Login.route) { LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route) },
                onLoginSuccess = { role -> 
                    val destination = if (role == "admin") Screen.AdminPanel.route else Screen.Homepage.route
                    navController.navigate(destination) {
                        popUpTo(Screen.Login.route) {inclusive=true}
                    }
                },
                authViewModel
            ) }

        //ödev 1 :kayıtol registerde succes yapısı oluştur ödev1 logindeki gibi
        composable(Screen.Register.route) { RegisterScreen(
            onNavigateToLogin = { navController.navigate(Screen.Login.route) },
            onRegisterSuccess = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                }
            },
            authViewModel
        ) }
        composable(Screen.Homepage.route){
            HomeScreen(
                authViewModel = authViewModel,
                bookViewModel = bookViewModel,
                borrowViewModel = borrowViewModel,
                onNavigateToBorrowings = { navController.navigate(Screen.Borrowings.route) }
            )
        }
        composable(Screen.Borrowings.route) {
            BorrowingsScreen(borrowViewModel)
        }
        composable(Screen.AdminPanel.route) {
            AdminScreen(
                adminViewModel = adminViewModel,
                authViewModel = authViewModel,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true } // Yığını tamamen temizle
                    }
                }
            )
        }
    }
}