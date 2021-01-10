package com.finbox.locationapi.modules

import com.finbox.locationapi.viewmodels.BusinessViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module





val requestViewModel = module  {
    viewModel{ BusinessViewModel(get(),get()) }

}

val viewModules = listOf(requestViewModel)

