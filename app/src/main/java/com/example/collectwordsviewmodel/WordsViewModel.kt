package com.example.collectwordsviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordsViewModel : ViewModel() {
    // inspiration from https://www.phind.com/search?cache=apyn2kifnsb6ctek28qvsxs8
    private var _words: MutableLiveData<List<String>> = MutableLiveData()
    // read-only version of _words
    val words: LiveData<List<String>> get() = _words

    fun add(newWord: String) {
        val currentItems = _words.value?: emptyList()
        _words.value = currentItems + newWord
    }

    fun clear() {
        _words.value = emptyList()
    }

    fun remove(word: String) {
        val value: List<String>? = _words.value
        if (value != null) {
            _words.value = value - word
        }
    }
}