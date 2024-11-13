package com.example.virgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.virgo.ui.screen.ecommerce.ProductDetailScreen
import com.example.virgo.ui.screen.home.HomeScreen
import com.example.virgo.ui.screen.search.SearchScreen
import com.example.virgo.route.HomeRoute
import com.example.virgo.route.ecommerce.ProductDetailRoute
import com.example.virgo.route.search.SearchRoute
import com.example.virgo.ui.theme.VirgoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        val navItems = listOf(
//            NavItem(
//                route = HomeRoute,
//                selectedIcon = Icons.Filled.Home,
//                unselectedIcon = Icons.Outlined.Home
//            ),
//            NavItem(
//
//            )
//        )

        setContent {
            VirgoTheme {
                val navController = rememberNavController()
                var selectedNavItemIndex by remember {
                    mutableIntStateOf(0)
                }

                Scaffold(
//                    bottomBar = {
//                        NavigationBar {
//                            navItems.forEachIndexed { index, navItem ->
//                                NavigationBarItem(
//                                    selected = selectedNavItemIndex == index,
//                                    onClick = {
//                                        selectedNavItemIndex = index
//                                        navController.navigate(navItem.route)
//                                    },
//                                    icon = {
//                                        Icon(
//                                            imageVector = if (selectedNavItemIndex == index) navItem.selectedIcon else navItem.unselectedIcon,
//                                            contentDescription = null)
//                                    })
//                            }
//                        }
//                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<HomeRoute> {
                            HomeScreen(navController)
                        }
                        composable<SearchRoute>{
                            SearchScreen()
                        }
                        composable<ProductDetailRoute>{
                            val args = it.toRoute<ProductDetailRoute>()
                            ProductDetailScreen()
                        }
                    }
                }
            }
        }
    }
}