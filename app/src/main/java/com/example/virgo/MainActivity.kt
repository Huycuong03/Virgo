package com.example.virgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.virgo.composable.auth.AuthScreen
import com.example.virgo.composable.home.HomeScreen
import com.example.virgo.route.auth.AuthRoute
import com.example.virgo.route.home.HomeRoute
import com.example.virgo.ui.theme.VirgoTheme

data class NavItem (
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badge: Int? = null
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val navItems = listOf(
            NavItem(
                route = AuthRoute,
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle
            ),
            NavItem(
                route = HomeRoute,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            )
        )

        setContent {
            VirgoTheme {
                val navController = rememberNavController()
                var selectedNavItemIndex by remember {
                    mutableIntStateOf(0)
                }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            navItems.forEachIndexed { index, navItem ->
                                NavigationBarItem(
                                    selected = selectedNavItemIndex == index,
                                    onClick = {
                                        selectedNavItemIndex = index
                                        navController.navigate(navItem.route)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (selectedNavItemIndex == index) navItem.selectedIcon else navItem.unselectedIcon,
                                            contentDescription = null)
                                    })
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AuthRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<AuthRoute> {
                            AuthScreen(navController)
                        }
                        composable<HomeRoute> {
                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}