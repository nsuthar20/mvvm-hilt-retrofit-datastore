package com.apps.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.apps.mvvm.databinding.ActivityMainBinding
import com.apps.mvvm.ui.base.BaseActivity
import com.apps.mvvm.ui.login.AuthFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .add(binding.mainFrame.id, AuthFragment())
            .commit()
    }
}