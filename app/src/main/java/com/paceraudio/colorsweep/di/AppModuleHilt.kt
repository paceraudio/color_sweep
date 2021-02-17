package com.paceraudio.colorsweep.di

import androidx.lifecycle.ViewModel
import com.paceraudio.colorsweep.util.Clog
import com.paceraudio.wire.util.ILogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object AppModuleHilt {

    @Provides
    fun provideLogger(): ILogger {
        return Clog()
    }

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}