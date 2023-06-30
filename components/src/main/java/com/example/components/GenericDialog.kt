package com.example.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericDialog(title: String, modifier: Modifier = Modifier, description: String?, onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = { onDismiss() }, modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(false) {},
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = title)
                    Spacer(modifier = Modifier.height(4.dp))

                    description?.let { description ->
                        Text(text = description)
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { onDismiss() },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Text(text = "Ok".uppercase())
                        }
                    }
                }
            }
        }
    }
}