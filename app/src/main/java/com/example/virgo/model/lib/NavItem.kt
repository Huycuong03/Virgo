package com.example.virgo.model.lib

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem (
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badge: Int? = null
)
