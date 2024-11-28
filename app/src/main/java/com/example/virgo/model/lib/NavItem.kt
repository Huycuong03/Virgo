package com.example.virgo.model.lib

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
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.virgo.route.HomeRoute
import com.example.virgo.route.TelemedicineRoute
import com.example.virgo.route.appointment.SearchFacilityRoute
import com.example.virgo.route.ecommerce.CartRoute

enum class NavItem (
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badge: Int? = null
) {
    HOME(
        route = HomeRoute,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    APPOINTMENT(
        route = SearchFacilityRoute,
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange
    ),
    TELEMEDICINE(
        route = TelemedicineRoute,
        selectedIcon = Icons.Filled.Call,
        unselectedIcon = Icons.Outlined.Call
    ),
    CART(
        route = CartRoute,
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart
    ),
    PROFILE(
        route = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
}
