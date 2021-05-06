package com.atechy.atechytwitterclone.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.atechy.atechytwitterclone.R
import com.atechy.atechytwitterclone.databinding.FragmentSignupBinding

/**
 * @author Shital Awathe
 * Created on date 06 May 2021
 *
 * This class is used to Sign up in application
 */
class SignUpFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSignupBinding>(inflater, R.layout.fragment_signup, container, false)
        binding.textSignIn?.setOnClickListener { view:View->
            view.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        return binding.root
    }
}