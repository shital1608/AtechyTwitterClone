package com.atechy.atechytwitterclone.signup

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atechy.atechytwitterclone.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author shital Awathe
 *
 * This is sign up view model contains the firebase user sign up logic
 */
class SignUpViewModel : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var databaseReference: DatabaseReference

    private val registeredSuccessFul: MutableLiveData<String> = MutableLiveData()
    val userRegisterResponse: LiveData<String>
        get() = registeredSuccessFul

    private val emailAlreadyExist: MutableLiveData<Boolean> = MutableLiveData()
    val mEmailAlreadyExist: LiveData<Boolean>
        get() = emailAlreadyExist

    companion object {
        val TAG = SignUpViewModel::class.qualifiedName
    }

    /**
     * Register user with name, email and password
     *
     * @param name
     * @param email
     * @param password
     */
    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !name.isNullOrEmpty()) {
                databaseReference = FirebaseDatabase.getInstance().reference.child("Users")
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = auth.currentUser
                        val user = User(uid = null, name = name, email = email)
                        databaseReference.child(firebaseUser.uid).setValue(user)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    registeredSuccessFul.postValue(Status.SUCCESS)
                                    Log.i(TAG, "User Register successfully")
                                }
                            }
                            .addOnFailureListener {
                                registeredSuccessFul.postValue(Status.FAIL)
                                Log.i(TAG, "${it?.message}")
                            }

                    } else {
                        emailAlreadyExist.postValue(true)
                        registeredSuccessFul.postValue(Status.ALREADY_EXIST)
                        Log.e(TAG, "Email address is already register")
                    }
                }

            }
        }

    }
}