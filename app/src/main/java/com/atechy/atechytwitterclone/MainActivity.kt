package com.atechy.atechytwitterclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.atechy.atechytwitterclone.login.LoginFragment
import com.atechy.atechytwitterclone.teamreply.TeamReplyFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth.getInstance as getInstance

/**
 * @author Shital Awathe
 *
 * Initiate login fragment or group chat fragment as per the current user status
 */
class MainActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = Firebase.auth
    private val teamReplyFragment = TeamReplyFragment()
    private val loginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Check current user if null start login screen else start team reply screen
         */
        if (auth.currentUser == null) {
            replaceFragment(R.id.container, loginFragment, false)
        } else {
            replaceFragment(R.id.container, teamReplyFragment, false)
        }

    }

    /**
     * Replace fragment
     */
    private fun replaceFragment(
        containerId: Int,
        fragmentInstance: Fragment,
        addToBackStack: Boolean = true
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragmentInstance)
        if (addToBackStack) {
            transaction.addToBackStack(fragmentInstance.javaClass.canonicalName)
        }
        transaction.commitAllowingStateLoss()
    }
}