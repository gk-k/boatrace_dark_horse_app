package com.gkk.darkhorseapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import darkhorseapp.composeapp.generated.resources.Res
import darkhorseapp.composeapp.generated.resources.compose_multiplatform
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        // Firestore state
        var firestoreMessage by remember { mutableStateOf("Not connected") }
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }

            // Firestore Test Button and Text
            Button(onClick = {
                coroutineScope.launch {
                    try {
                        firestoreMessage = "Connecting..."
                        val firestore = Firebase.firestore
                        val data = mapOf("field" to "Hello, iOS & Android!")
                        // Add a document to a "test" collection
                        firestore.collection("test").document("doc").set(data)
                        // Read the document
                        val doc = firestore.collection("test").document("doc").get()
                        firestoreMessage = "Firestore says: '${doc.get<String>("field")}'"
                    } catch (e: Exception) {
                        firestoreMessage = "Error: ${e.message}"
                    }
                }
            }) {
                Text("Connect to Firestore")
            }
            Text(firestoreMessage)
        }
    }
}
