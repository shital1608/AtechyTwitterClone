package com.atechy.atechytwitterclone.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.atechy.atechytwitterclone.R
import com.atechy.atechytwitterclone.databinding.FragmentLoginBinding

/**
 * @author Shital Awathe
 * Created on date 06 May 2021
 *
 * This class is used to login in application
 */
class LoginFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)
        binding.textSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment2)
        }
        binding.buttonSignIn.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_teamReplyFragment)
        }
        return binding.root
    }
}