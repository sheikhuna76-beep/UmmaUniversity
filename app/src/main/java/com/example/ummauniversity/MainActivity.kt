package com.example.ummauniversity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.ummauniversity.data.database.UmmaDatabase
import com.example.ummauniversity.data.repository.UmmaRepository
import com.example.ummauniversity.ui.navigation.AppNavGraph
import com.example.ummauniversity.ui.theme.UmmaUniversityTheme
import com.example.ummauniversity.viewmodel.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = UmmaDatabase.getInstance(applicationContext)
        val repository = UmmaRepository(db)
        val factory = ViewModelFactory(repository)

        val authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        val studentViewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]
        val lecturerViewModel = ViewModelProvider(this, factory)[LecturerViewModel::class.java]
        val adminViewModel = ViewModelProvider(this, factory)[AdminViewModel::class.java]

        setContent {
            UmmaUniversityTheme {
                val navController = rememberNavController()
                AppNavGraph(
                    navController = navController,
                    authViewModel = authViewModel,
                    studentViewModel = studentViewModel,
                    lecturerViewModel = lecturerViewModel,
                    adminViewModel = adminViewModel
                )
            }
        }
    }
}
