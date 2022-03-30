package com.example.retrofitpicasso

import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
        val countOfJokes by viewModel.count.observeAsState()
        val activeSources by viewModel.activeSources.observeAsState()

        Column(modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(8.dp),
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

            Spacer(modifier = Modifier.height(16.dp))

            //check boxes for different sources
            Text("Joke types")
            Spacer(Modifier.height(16.dp))
            Column(Modifier.wrapContentWidth(Alignment.Start)) {
                //geek
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = activeSources?.geek ?: false,
                        onCheckedChange = viewModel.onGeekSourceChkBoxClicked)
                    Text("Geek jokes", color = Color.Black, fontSize = 20.sp)
                }
                //dad
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = activeSources?.dad ?: false,
                        onCheckedChange = viewModel.onDadSourceChkBoxClicked)
                    Text("Dad jokes", color = Color.Black, fontSize = 20.sp)
                }
                //setup
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = activeSources?.setup ?: false,
                        onCheckedChange = viewModel.onSetupSourceChkBoxClicked)
                    Text("Setup jokes", color = Color.Black, fontSize = 20.sp)
                }
                //blague
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = activeSources?.blague ?: false,
                        onCheckedChange = viewModel.onBlagueSourceChkBoxClicked)
                    Text("Blague jokes", color = Color.Black, fontSize = 20.sp)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview() {
        ActivityUi()
    }
}
