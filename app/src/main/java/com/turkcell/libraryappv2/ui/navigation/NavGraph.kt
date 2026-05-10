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
import com.turkcell.libraryappv2.ui.screen.SplashScreen
import com.turkcell.libraryappv2.ui.viewmodel.AuthViewModel
import com.turkcell.libraryappv2.ui.viewmodel.BookViewModel


@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    //her bir screen için viewmodel yazmaktan kurulmak için

    val authViewModel: AuthViewModel = viewModel() // oluşturulma aşaması.. init {}
    val bookViewModel: BookViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Splash.route)
    {
        composable(Screen.Splash.route) {
            SplashScreen(authViewModel,
                onAuthenticated = { role ->
                    navController.navigate(Screen.Homepage.route){
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
                onLoginSuccess = {role -> navController.navigate(Screen.Homepage.route) {
                    popUpTo(Screen.Login.route) {inclusive=true}
                    // Yığın yalnızca verilen URL ile kalacaktı (false)
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
            HomeScreen(authViewModel,bookViewModel)
        }
    }
}