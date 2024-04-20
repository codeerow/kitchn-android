package com.spirit.kitchn.ui.screen.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.kitchn.ui.component.KButton
import com.spirit.kitchn.ui.theme.KTheme
import com.stevdzasan.onetap.rememberOneTapSignInState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    isPreview: Boolean = false,
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
) {
//    val state = rememberOneTapSignInState()
//
//    if (!isPreview) {
//        OneTapSignInWithGoogle(
//            state = state,
//            clientId = "998789658191-tk4052teeaesflsjpin2mgn1m372gdtg.apps.googleusercontent.com",
//            onTokenIdReceived = { tokenId ->
//                Log.d("LOG", tokenId)
//            },
//            onDialogDismissed = { message ->
//                Log.d("LOG", message)
//            }
//        )
//    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Welcome to Kitchn") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.weight(1f))
            EmailField(value = email, onValueChanged = onEmailChanged)
            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(value = password, onValueChanged = onPasswordChanged)
            Spacer(modifier = Modifier.height(48.dp))

            KButton(
                onClick = onLoginClicked,
                modifier = Modifier
                    .testTag("buttonLogin")
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = "Login"
            )
            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@Composable
private fun EmailField(value: String, onValueChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text("Email")
        }
    )
}

@Composable
private fun PasswordField(value: String, onValueChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text("Password")
        }
    )
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    KTheme {
        WelcomeScreen(
            isPreview = true,
            email = "",
            password = "",
            onEmailChanged = {},
            onPasswordChanged = {},
            onLoginClicked = {},
        )
    }
}
