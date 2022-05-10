package com.apps.mvvm.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apps.mvvm.data.network.Resource
import com.apps.mvvm.databinding.FragmentAuthBinding
import com.apps.mvvm.ui.base.BaseFragment
import com.apps.mvvm.util.enable
import com.apps.mvvm.util.handleApiError
import com.apps.mvvm.util.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthBinding
        get() = FragmentAuthBinding::inflate

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            viewModel.doLogin(
                requireContext(),
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        viewModel.authResponse.observe(viewLifecycleOwner) {
            binding.btnLogin.enable(it !is Resource.Loading)
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthKey(it.value.data.auth_token)
                    }
                    requireView().snackBar("Login Successful!")
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
            }
        }
    }
}