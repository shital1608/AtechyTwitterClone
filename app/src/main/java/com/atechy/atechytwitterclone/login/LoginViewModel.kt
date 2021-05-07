package com.atechy.atechytwitterclone.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atechy.atechytwitterclone.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author Shital Awathe
 * Created on date 6th May 2021
 *
 * Login view model contails the business logic of login screen
 */
class LoginViewModel: ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var databaseReference: DatabaseReference

    private val loginSuccessFul: MutableLiveData<String> = MutableLiveData()
    val loginResponse: LiveData<String>
        get() = loginSuccessFul

    companion object{
        val TAG = LoginViewModel::class.qualifiedName
    }

    /**
     * Check email validation
     */
    private fun checkEmailValidation(email :String):Boolean{
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    /**
     * Login in application
     *
     * @param email
     * @param password
     */
    fun loginApplication(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO){
            if (auth.currentUser == null) {
                databaseReference = FirebaseDatabase.getInstance().reference.child("Users")
                if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    if (checkEmailValidation(email)) {
                        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                            if (it.isSuccessful) {
                                loginSuccessFul.postValue(Status.SUCCESS)
                            } else {
                                loginSuccessFul.postValue("Authentication Fail")
                                Log.e(TAG, "Authentication Fail")
                            }
                        }
                    } else {
                        loginSuccessFul.postValue("Please enter valid email")
                        Log.e(TAG, "Please enter valid email")
                    }
                } else {
                    loginSuccessFul.postValue("Email and password should not be blank")
                    Log.e(TAG, "Email or password should not be blank")
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.e("LoginViewModel", "Destroyed!")
    }
}