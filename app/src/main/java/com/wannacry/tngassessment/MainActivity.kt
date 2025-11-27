package com.wannacry.tngassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wannacry.tngassessment.presentation.screen.UsersScreen
import com.wannacry.tngassessment.presentation.viewmodel.UsersViewModel
import com.wannacry.tngassessment.ui.theme.TNGAssessmentTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TNGAssessmentTheme {
                val viewModel: UsersViewModel = getViewModel()
                UsersScreen(viewModel, this@MainActivity)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TNGAssessmentTheme {
        val viewModel = getViewModel<UsersViewModel>()
        val context = MainActivity()
        UsersScreen(viewModel, context)
    }
}