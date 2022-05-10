package com.apps.mvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.apps.mvvm.data.datastore.DataPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity<V : ViewBinding> : AppCompatActivity() {
    protected open lateinit var binding: V
    abstract val bindingInflater: (LayoutInflater) -> V

    @Inject
    protected lateinit var dataPreferences: DataPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            dataPreferences.authKey.first()
        }
    }
}