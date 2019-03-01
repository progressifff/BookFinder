package com.progressifff.bookfinder.dagger

import com.progressifff.bookfinder.RxBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RxBusModule {
    @Singleton
    @Provides
    fun eventBus(): RxBus = RxBus
}