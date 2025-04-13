package com.example.cipherpocketbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cipherpocketbook.screens.ADFGVXScreen
import com.example.cipherpocketbook.screens.CaesarScreen
import com.example.cipherpocketbook.screens.ColumnarScreen
import com.example.cipherpocketbook.screens.HomeScreen
import com.example.cipherpocketbook.screens.HillScreen
import com.example.cipherpocketbook.screens.PlayfairScreen
import com.example.cipherpocketbook.screens.VigenereScreen
import com.example.cipherpocketbook.ui.theme.CipherPocketBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CipherPocketBookTheme {

                // Instantiate the navController and screen composables
                val nav = rememberNavController()
                NavHost(
                    navController = nav,
                    startDestination = NavScreen.HomeScreen
                ) {
                    composable<NavScreen.HomeScreen>     { HomeScreen(nav) }
                    composable<NavScreen.CaesarScreen>   { CaesarScreen   { nav.popBackStack() } }
                    composable<NavScreen.ADFGVXScreen>   { ADFGVXScreen   { nav.popBackStack() } }
                    composable<NavScreen.ColumnarScreen> { ColumnarScreen { nav.popBackStack() } }
                    composable<NavScreen.HillScreen>     { HillScreen     { nav.popBackStack() } }
                    composable<NavScreen.PlayfairScreen> { PlayfairScreen { nav.popBackStack() } }
                    composable<NavScreen.VigenereScreen> { VigenereScreen { nav.popBackStack() } }
                }

            }
        }
    }
}
