package com.example.loginapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class AuthViewModel:ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    var _authstate = MutableLiveData<AuthState>()
    var authState : LiveData<AuthState>  = _authstate


    init{
        checkAuthStatus()
    }
    fun checkAuthStatus(){
        val currentUser = auth.currentUser
        if (currentUser == null){
            _authstate.value = AuthState.UnAuthenticated
        }else{
            _authstate.value = AuthState.Authenticated
        }
    }
    fun login(email:String,password:String){
        if(email.isEmpty() || password.isEmpty()){
            _authstate.value=AuthState.Error("Email or password can not be empty")
            return
        }
        _authstate.value=AuthState.Loading
        if(auth.currentUser?.isEmailVerified == true){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    _authstate.value=AuthState.Authenticated
                }else{
                    _authstate.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    } else {
            _authstate.value = AuthState.Error("Please verify your email")
    }
    }

    fun signup(email:String,password:String){
        if(email.isEmpty() || password.isEmpty()){
            _authstate.value=AuthState.Error("Email or password can not be empty")
            return
        }
        _authstate.value=AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    auth.currentUser?.sendEmailVerification()
                    _authstate.value=AuthState.Authenticated
                }else{
                    _authstate.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authstate.value=AuthState.UnAuthenticated
    }

}


sealed class AuthState{
    object Authenticated : AuthState()
    object UnAuthenticated : AuthState()
    object  Loading :AuthState()
    data class Error(val message : String)  : AuthState()
}

