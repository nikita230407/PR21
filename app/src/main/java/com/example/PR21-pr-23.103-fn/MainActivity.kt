package com.example.pz21last

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.pz21last.navigation.AppNavigation
import com.example.pz21last.ui.theme.PZ21LastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PZ21LastTheme {
                Scaffold { padding ->
                    AppNavigation(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}
