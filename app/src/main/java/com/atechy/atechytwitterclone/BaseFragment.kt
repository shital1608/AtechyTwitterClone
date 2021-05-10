package com.atechy.atechytwitterclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author shital awathe
 *
 * Cretaed base class for all fragments
 */
abstract class BaseFragment<dataBinding: ViewDataBinding>:Fragment() {

    open lateinit var binding:dataBinding

    private fun init(inflater: LayoutInflater, container: ViewGroup) {
       binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        init(inflater, container!!)
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int


}