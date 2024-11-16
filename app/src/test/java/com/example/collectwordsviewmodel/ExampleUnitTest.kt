package com.example.collectwordsviewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ExampleUnitTest {
    // https://stackoverflow.com/questions/58057769/method-getmainlooper-in-android-os-looper-not-mocked-still-occuring-even-after-a
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    // build.gradle.kts: testImplementation("androidx.arch.core:core-testing:...")

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun viewModel_test() {
        val viewModel = WordsViewModel()

        viewModel.add("hello")
        viewModel.add("world")
        assertEquals(listOf("hello", "world"), viewModel.words.value)
        viewModel.remove("hello")
        assertEquals(listOf("world"), viewModel.words.value)
        viewModel.clear()
        assertEquals(emptyList<String>(), viewModel.words.value)
    }
}