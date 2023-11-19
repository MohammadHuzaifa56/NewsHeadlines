package com.example.newsheadlines.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.example.newsheadlines.presentation.main.biometric.BiometricAuthenticationScreen
import com.example.newsheadlines.ui.theme.NewsHeadlinesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsHeadlinesTheme {
                BiometricAuthenticationScreen()
            }
        }
    }
}