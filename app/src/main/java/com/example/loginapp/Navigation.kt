package com.example.loginapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginapp.screens.home.HomeScreen
import com.example.loginapp.screens.login.LogInScreen
import com.example.loginapp.screens.signup.SignupScreen

@Composable
fun MyAppNavigation(modifier: Modifier,authViewModel: AuthViewModel){
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "home", builder = {
        composable("home"){
            HomeScreen(authViewModel,navController)
        }
        composable("login"){
            LogInScreen(modifier=Modifier,navController, authViewModel)
        }
        composable("signup"){
            SignupScreen(modifier = Modifier,navController, authViewModel )
        }
    })
}