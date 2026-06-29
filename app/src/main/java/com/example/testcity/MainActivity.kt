package com.example.testcity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.testcity.core.ui.theme.TestCityTheme
import com.example.testcity.navigation.TestCityNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestCityTheme {
                TestCityNavHost(navController = rememberNavController())
            }
        }
    }
}
