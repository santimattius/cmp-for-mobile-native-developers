package com.santimattius.kmp.skeleton.core.arch

import kotlinx.coroutines.flow.StateFlow

expect class StateFlowWrapper<T>(flow: StateFlow<T>) {
    val value: T
}
