package com.example.retrofitpicasso.composeActivity

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActivityUi(viewModel: ComposeActivityViewModel = ComposeActivityViewModel()) {
    val state by viewModel.state.observeAsState()

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = state?.joke ?: "null",
            color = Color.Black,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = viewModel.onNextJokeBtnClick) {
            Text("Get next joke")
        }

        Text("You've asked for ${state?.countOfJokes ?: -1} jokes")
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ActivityUi()
}