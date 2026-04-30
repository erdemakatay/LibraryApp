package com.turkcell.libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.turkcell.libraryapp.ui.navigation.NavGraph
import com.turkcell.libraryapp.ui.screen.LoginScreen
import com.turkcell.libraryapp.ui.screen.RegisterScreen
import io.github.jan.supabase.auth.admin.LinkType


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavGraph()
        }
    }
}

