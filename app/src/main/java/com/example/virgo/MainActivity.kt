package com.example.virgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.virgo.model.lib.NavItem
import com.example.virgo.route.ArticleRoute
import com.example.virgo.ui.screen.ecommerce.ProductDetailScreen
import com.example.virgo.ui.screen.home.HomeScreen
import com.example.virgo.ui.screen.search.SearchScreen
import com.example.virgo.route.HomeRoute
import com.example.virgo.route.TelemedicineRoute
import com.example.virgo.route.appointment.AppointmentBookingRoute
import com.example.virgo.route.appointment.AppointmentHistoryRoute
import com.example.virgo.route.appointment.FacilityDetailRoute
import com.example.virgo.route.appointment.SearchFacilityRoute
import com.example.virgo.route.ecommerce.ProductDetailRoute
import com.example.virgo.route.search.SearchRoute
import com.example.virgo.ui.screen.appointment.AppointmentBookingScreen
import com.example.virgo.ui.screen.appointment.FacilityDetailScreen
import com.example.virgo.ui.screen.appointment.SearchFacilityScreen
import com.example.virgo.ui.screen.article.ArticleScreen
import com.example.virgo.ui.screen.profile.AppointmentHistoryScreen
import com.example.virgo.ui.screen.telemedicine.TelemedicineScreen
import com.example.virgo.ui.theme.VirgoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val navItems = listOf(
            NavItem(
                route = HomeRoute,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            NavItem(
                route = SearchFacilityRoute,
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange
            ),
            NavItem(
                route = TelemedicineRoute,
                selectedIcon = Icons.Filled.Call,
                unselectedIcon = Icons.Outlined.Call
            ),
            NavItem(
                route = "Cart",
                selectedIcon = Icons.Filled.ShoppingCart,
                unselectedIcon = Icons.Outlined.ShoppingCart
            ),
            NavItem(
                route = "Profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            )
        )

        setContent {
            VirgoTheme {
                val navController = rememberNavController()
                var selectedNavItemIndex by remember {
                    mutableIntStateOf(0)
                }
                val currentRoute = navController.currentBackStackEntryAsState()
                Scaffold(
                    bottomBar = {
                        if ( shouldShowNavBar(currentRoute.value?.destination?.route) ) {
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
                    }
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
                            SearchScreen(navController)
                        }
                        composable<ProductDetailRoute>{
                            val id = it.toRoute<ProductDetailRoute>().id
                            ProductDetailScreen(id, navController)
                        }
                        composable<SearchFacilityRoute> {
                            SearchFacilityScreen(navController)
                        }
                        composable<TelemedicineRoute> {
                            TelemedicineScreen()
                        }
                        composable<ArticleRoute> {
                            val html: String = it.toRoute<ArticleRoute>().html
                            ArticleScreen(html)
                        }
                        composable<FacilityDetailRoute> {
                            val id = it.toRoute<FacilityDetailRoute>().id
                            FacilityDetailScreen(id, navController)
                        }
                        composable<AppointmentBookingRoute> {
                            val facilityId = it.toRoute<AppointmentBookingRoute>().facilityId
                            AppointmentBookingScreen(facilityId, navController)
                        }
                        composable<AppointmentHistoryRoute> {
                            AppointmentHistoryScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

fun shouldShowNavBar (route: String?): Boolean {
    val routeWithNavBar = listOf(
        "HomeRoute", "SearchFacilityRoute"
    )
    return route.isNullOrEmpty() or routeWithNavBar.contains(route?.split(".")?.lastOrNull())
}
