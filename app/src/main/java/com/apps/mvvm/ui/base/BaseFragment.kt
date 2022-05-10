package com.apps.mvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.apps.mvvm.data.datastore.DataPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment<B : ViewBinding> : Fragment() {
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

    @Inject
    protected lateinit var dataPreferences: DataPreferences
    protected open lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (::binding.isInitialized) {
            return binding.root
        }
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            dataPreferences.authKey.first()
        }
    }
}