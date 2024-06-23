package com.example.collectwordsviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordsViewModel : ViewModel() {
    // inspiration from https://www.phind.com/search?cache=apyn2kifnsb6ctek28qvsxs8
    var words: MutableLiveData<List<String>> = MutableLiveData()
        private set

    fun add(newWord: String) {
        val currentItems = words.value ?: emptyList()
        words.value = currentItems + newWord
    }

    fun clear() {
        words.value = emptyList()
    }

    fun remove(word: String) {
        val value: List<String>? = words.value
        if (value != null) {
            words.value = value - word
        }
    }
}