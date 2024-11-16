package com.example.collectwordsviewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collectwordsviewmodel.ui.theme.CollectWordsViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CollectWordsViewModelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel : WordsViewModel= viewModel() // persistence
                    // viewModel.words = MutableLiveData(listOf<String>())
                    // Not legal, due to "private set"
                    CollectWords(modifier = Modifier.padding(innerPadding),
                        words = viewModel.words.observeAsState().value,
                        onAdd = { word -> viewModel.add(word) },
                        onRemove = { word -> viewModel.remove(word) },
                        onClear = { viewModel.clear() }
                    )
                }
            }
        }
    }
}

@Composable
fun CollectWords(
    words: List<String>?,
    modifier: Modifier = Modifier,
    onAdd: (String) -> Unit = {},
    onRemove: (String) -> Unit = {},
    onClear: () -> Unit = {}
) {
    // Add to gradle file
    // https://tigeroakes.com/posts/mutablestateof-list-vs-mutablestatelistof/
    // val words = viewModel.words.observeAsState()
    var word by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var showList by remember { mutableStateOf(true) }

    Column(modifier = modifier) {
        Text(text = "Collect words", style = MaterialTheme.typography.headlineLarge)
        OutlinedTextField(
            value = word,
            onValueChange = { word = it },
            // https://medium.com/@GkhKaya00/exploring-keyboard-types-in-kotlin-jetpack-compose-ca1f617e1109
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter a word") }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { onAdd(word) }) {
                Text("Add")
            }
            Button(onClick = {
                onClear()
                word = ""
                result = ""
            }) {
                Text("Clear")
            }
            Button(onClick = { result = words.toString() }) {
                Text("Show")
            }
        }
        if (result.isNotEmpty()) {
            Text(result)
        } else {
            Text("Empty", fontStyle = FontStyle.Italic)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show list")
            Spacer(modifier = Modifier.padding(5.dp))
            Switch(checked = showList, onCheckedChange = { showList = it })
        }
        if (showList) {
            if (words.isNullOrEmpty()) {
                Text("No words")
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(words) { word: String ->
                        Text(word, modifier = Modifier.clickable { onRemove(word) })
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CollectWordsPreview() {
    CollectWordsViewModelTheme {
        CollectWords(words = listOf("Hello", "World"))
    }
}