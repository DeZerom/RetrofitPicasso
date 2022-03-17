package com.example.retrofitpicasso

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofitpicasso.ui.theme.RetrofitPicassoTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitPicassoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ActivityUi()
                }
            }
        }
    }

    @Composable
    fun ActivityUi(viewModel: ComposeActivityViewModel = ComposeActivityViewModel(application)) {
        val joke by viewModel.activeJoke.observeAsState()
        val countOfJokes by viewModel.countOfJokes.observeAsState()

        Log.i("ComposeActivityUI", joke?.joke ?: "")

        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = joke?.joke ?: "Your joke is loading. Wait for a minute",
                color = Color.Black,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = viewModel.onNextJokeBtnClick) {
                Text("Get next joke")
            }

            Text("You've asked for ${countOfJokes ?: -666} jokes")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview() {
        ActivityUi()
    }
}
