package com.atechy.atechytwitterclone.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.atechy.atechytwitterclone.BaseFragment
import com.atechy.atechytwitterclone.R
import com.atechy.atechytwitterclone.Status
import com.atechy.atechytwitterclone.databinding.FragmentSignupBinding
import com.atechy.atechytwitterclone.login.LoginFragment
import com.atechy.atechytwitterclone.teamreply.TeamReplyFragment


/**
 * @author Shital Awathe
 * Created on date 06 May 2021
 *
 * This class is used to Sign up in application
 */
class SignUpFragment : BaseFragment<FragmentSignupBinding>() {
    companion object {
        val TAG = SignUpFragment::class.qualifiedName
    }

    private lateinit var signUpViewModel: SignUpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        observeData()
        onClick()
    }

    /**
     * on click events
     */
    private fun onClick() {
        binding.textSignIn?.setOnClickListener { view: View ->
            fragmentManager?.beginTransaction()?.replace(R.id.container, LoginFragment())?.commit()
        }

        /**
         * Sign up button click
         */
        binding.buttonSignUp.setOnClickListener {
            if (binding.edtName.text.toString().isBlank() || binding.edtEmail.text.toString()
                            .isBlank() || binding.edtPassword.text.toString()
                            .isBlank() || binding.edtConfirmPassword.text.toString().isBlank()
            ) {
                displayToast(resources.getString(R.string.feilds_should_not_be_empty))
            } else {
                binding.progressBar.visibility = View.VISIBLE
                signUpViewModel.registerUser(
                        binding.edtName.text.toString(),
                        binding.edtEmail.text.toString(),
                        binding.edtPassword.text.toString()
                )
            }
        }
    }

    /**
     * Observe data
     */
    private fun observeData() {
        signUpViewModel.userRegisterResponse.observe(
                viewLifecycleOwner,
                Observer { response: String ->
                    binding.progressBar.visibility = View.GONE
                    loadTeamReplayFragment(response)
                })
        signUpViewModel.mEmailAlreadyExist.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.edtName.setText("")
                binding.edtPassword.setText("")
                binding.edtConfirmPassword.setText("")
                binding.edtEmail.setText("")
            }
        })
    }

    /**
     *
     * @param response
     */
    private fun loadTeamReplayFragment(response: String) {
        if (response == Status.SUCCESS) {
            fragmentManager?.beginTransaction()?.replace(R.id.container, TeamReplyFragment())
                ?.commit()
            displayToast(resources.getString(R.string.register_success))
        } else if (response == Status.FAIL) {
            displayToast(resources.getString(R.string.register_fail))
        } else {
            displayToast(resources.getString(R.string.email_already_register))
        }
    }

    /**
     * Display text
     */
    private fun displayToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun getLayoutRes() =  R.layout.fragment_signup
}