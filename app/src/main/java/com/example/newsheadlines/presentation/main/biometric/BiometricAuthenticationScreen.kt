package com.example.newsheadlines.presentation.main.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.newsheadlines.presentation.main.MainView

@Composable
fun BiometricAuthenticationScreen() {
    val context = LocalContext.current
    val biometricManager = remember { BiometricManager.from(context) }
    val isBiometricAvailable = remember {
        biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
    }

    val isBiometricAuthenticationSuccess = remember{
        mutableStateOf(false)
    }

    when (isBiometricAvailable) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            BiometricAuthenticationFlow(
                context = context,
                isBiometricAuthenticationSuccess = isBiometricAuthenticationSuccess
            )
        }

        else -> {
            MainView()
        }
    }
}

@Composable
private fun BiometricAuthenticationFlow(
    context: Context,
    isBiometricAuthenticationSuccess: MutableState<Boolean>
) {
    val executor = remember { ContextCompat.getMainExecutor(context) }
    val biometricPrompt = remember {
        BiometricPrompt(
            context as FragmentActivity,
            executor,
            biometricCallback(isBiometricAuthenticationSuccess)
        )
    }

    LaunchedEffect(Unit) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication is required")
            .setSubtitle("Place your finger on the fingerprint sensor")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    if (isBiometricAuthenticationSuccess.value) {
        MainView()
    }
}

private fun biometricCallback(
    isBiometricAuthenticationSuccess: MutableState<Boolean>
): BiometricPrompt.AuthenticationCallback {
    return object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            isBiometricAuthenticationSuccess.value = false
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            isBiometricAuthenticationSuccess.value = true
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            isBiometricAuthenticationSuccess.value = false
        }
    }
}