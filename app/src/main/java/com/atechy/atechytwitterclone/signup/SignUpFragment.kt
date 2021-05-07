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
import com.atechy.atechytwitterclone.R
import com.atechy.atechytwitterclone.Status
import com.atechy.atechytwitterclone.databinding.FragmentSignupBinding
import com.atechy.atechytwitterclone.login.LoginFragment


/**
 * @author Shital Awathe
 * Created on date 06 May 2021
 *
 * This class is used to Sign up in application
 */
class SignUpFragment : Fragment() {
    companion object {
        val TAG = SignUpFragment::class.qualifiedName
    }

    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSignupBinding>(
            inflater,
            R.layout.fragment_signup,
            container,
            false
        )
        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        signUpViewModel.userRegisterResponse.observe(
            viewLifecycleOwner,
            Observer { response: String ->
                binding.progressBar.visibility = View.GONE
                loadLoginFragment(response)
            })
        signUpViewModel.mEmailAlreadyExist.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.edtName.setText("")
                binding.edtPassword.setText("")
                binding.edtConfirmPassword.setText("")
                binding.edtEmail.setText("")
            }
        })

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
                Toast.makeText(
                    context,
                    resources.getString(R.string.feilds_should_not_be_empty),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                binding.progressBar.visibility = View.VISIBLE
                signUpViewModel.registerUser(
                    binding.edtName.text.toString(),
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString()
                )
            }
        }
        return binding.root
    }

    /**
     *
     * @param response
     */
    private fun loadLoginFragment(response: String) {
        if (response == Status.SUCCESS) {
            fragmentManager?.beginTransaction()?.replace(R.id.container, LoginFragment())?.commit()
            Toast.makeText(
                activity,
                resources.getString(R.string.register_success),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(activity, response, Toast.LENGTH_SHORT).show()
        }
    }
}