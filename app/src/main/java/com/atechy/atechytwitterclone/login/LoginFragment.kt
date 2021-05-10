package com.atechy.atechytwitterclone.login

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.atechy.atechytwitterclone.base.BaseFragment
import com.atechy.atechytwitterclone.R
import com.atechy.atechytwitterclone.Status
import com.atechy.atechytwitterclone.databinding.FragmentLoginBinding
import com.atechy.atechytwitterclone.signup.SignUpFragment
import com.atechy.atechytwitterclone.teamreply.TeamReplyFragment


/**
 * @author Shital Awathe
 * Created on date 06 May 2021
 *
 * This class is used to login in application
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    companion object {
        val TAG = LoginFragment::class.qualifiedName
    }

    private lateinit var loginViewModel: LoginViewModel

    /**
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBackEvent(binding.root)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        observeData()
        binding.textSignUp.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container, SignUpFragment())?.addToBackStack("tag")?.commit()
        }
        binding.buttonSignIn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if (!binding.editEmail.text.toString()
                            .isNullOrEmpty() && !binding.editPassword.text.toString().isNullOrEmpty()
            ) {
                if (loginViewModel.checkEmailValidation(binding.editEmail.text.toString())) {
                    loginViewModel.loginApplication(
                            binding.editEmail.text.toString(),
                            binding.editPassword.text.toString()
                    )
                } else {
                    Toast.makeText(
                            activity,
                            resources.getString(R.string.please_enter_valid_email),
                            Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                        activity,
                        resources.getString(R.string.email_pass_should_not_be),
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Observe data
     */
    private fun observeData() {
        loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer { response: String ->
            loadTeamReplyFragment(response)
        })
    }

    /**
     * Load team reply fragment
     *
     * @param loginResponse
     * @param binding
     */
    private fun loadTeamReplyFragment(loginResponse: String) {
        binding.progressBar.visibility = View.GONE
        if (loginResponse == Status.SUCCESS) {
            Toast.makeText(activity, resources.getString(R.string.login_success), Toast.LENGTH_SHORT).show()
            fragmentManager?.beginTransaction()?.replace(R.id.container, TeamReplyFragment())
                ?.commit()
        } else {
            Toast.makeText(activity, resources.getString(R.string.auth_fail), Toast.LENGTH_SHORT)
                .show()
        }
    }

    /**
     * Handle back event
     */
    private fun handleBackEvent(view: View){
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action === KeyEvent.ACTION_UP) {
                activity?.finish()
            }
            false
        }
    }

    /**
     * get resource layout for login fragment
     */
    override fun getLayoutRes() =  R.layout.fragment_login;

}