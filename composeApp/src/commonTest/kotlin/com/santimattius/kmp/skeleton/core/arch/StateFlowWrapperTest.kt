package com.santimattius.kmp.skeleton.core.arch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.test.Test
import kotlin.test.assertEquals

class StateFlowWrapperTest {

    @Test
    fun wrapperReturnsInitialValue() {
        val flow = MutableStateFlow(42)
        val wrapper = StateFlowWrapper(flow)
        assertEquals(42, wrapper.value)
    }

    @Test
    fun wrapperReflectsUpdatedFlowValue() {
        val flow = MutableStateFlow("initial")
        val wrapper = StateFlowWrapper(flow)
        flow.value = "updated"
        assertEquals("updated", wrapper.value)
    }
}
