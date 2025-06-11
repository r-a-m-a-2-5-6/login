package com.example.loginapp.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.loginapp.AuthState
import com.example.loginapp.AuthViewModel


@Composable

fun HomeScreen(authViewModel: AuthViewModel,navController:NavController){
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
 LaunchedEffect(authState.value) {
  when(authState.value){
   is AuthState.UnAuthenticated -> navController.navigate("login")
   is AuthState.Error -> Toast.makeText(context,
    (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
   else -> Unit
  }
 }


      Column(modifier = Modifier.padding(all = 8.dp),
       verticalArrangement = Arrangement.Center) {
           Text("This is cMahanadi ")
       Spacer(modifier = Modifier.height(16.dp))
       Button(onClick = {
        authViewModel.signout()
       }){
        Text("Signout")
       }



}


}