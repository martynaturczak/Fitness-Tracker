package com.example.fittracker

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(color = Color(0xFFC1F0F0))
    ) {
        Text(
            text = "Fitness Tracker",
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            color = LocalContentColor.current
        )
    }
    Text(
        text = "Witamy w aplikacji!",
        modifier = Modifier
            .padding(top = 180.dp, bottom = 15.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        color = LocalContentColor.current
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Text(
            text = "Logowanie",
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = LocalContentColor.current
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Hasło") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onLoginSuccess()
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        }
                    }
            },
            modifier = Modifier
                .padding(16.dp)
                .width(150.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color(0xFF29A3A3)
            )
        ) {
            Text("Zaloguj")
        }
        TextButton(
            onClick = { navController.navigate("register") },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Nie masz konta? Zarejestruj się")
        }
    }

}
