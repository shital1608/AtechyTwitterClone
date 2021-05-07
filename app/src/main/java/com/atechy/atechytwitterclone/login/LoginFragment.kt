package com.atechy.atechytwitterclone.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
class LoginFragment : Fragment() {
    companion object {
        val TAG = LoginFragment::class.qualifiedName
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        observeData(binding)

        binding.textSignUp.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container, SignUpFragment())?.commit()
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
        return binding.root
    }

    /**
     * Observe data
     */
    private fun observeData(binding: FragmentLoginBinding) {
        loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer { response: String ->
            loadTeamReplyFragment(response, binding)
        })
    }

    /**
     * Load team reply fragment
     *
     * @param loginResponse
     * @param binding
     */
    private fun loadTeamReplyFragment(loginResponse: String, binding: FragmentLoginBinding) {
        binding.progressBar.visibility = View.GONE
        if (loginResponse == Status.SUCCESS) {
            Toast.makeText(activity, loginResponse, Toast.LENGTH_SHORT).show()
            fragmentManager?.beginTransaction()?.replace(R.id.container, TeamReplyFragment())
                ?.commit()
        } else {
            Toast.makeText(activity, resources.getString(R.string.auth_fail), Toast.LENGTH_SHORT)
                .show()
        }
    }

}