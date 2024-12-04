package com.example.virgo

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.virgo.alarm.AlarmReceiver
import com.example.virgo.model.lib.Alarm
import com.example.virgo.model.ecommerce.OrderStatus
import com.example.virgo.model.lib.NavItem
import com.example.virgo.model.lib.Reminder
import com.example.virgo.repository.SharedPreferencesManager
import com.example.virgo.route.ArticleRoute
import com.example.virgo.route.CustomNavType
import com.example.virgo.ui.screen.ecommerce.ProductDetailScreen
import com.example.virgo.ui.screen.home.HomeScreen
import com.example.virgo.ui.screen.search.SearchScreen
import com.example.virgo.route.HomeRoute
import com.example.virgo.route.LoginRoute
import com.example.virgo.route.SignupRoute
import com.example.virgo.route.TelemedicineRoute
import com.example.virgo.route.appointment.AppointmentBookingRoute
import com.example.virgo.route.appointment.AppointmentHistoryRoute
import com.example.virgo.route.appointment.FacilityDetailRoute
import com.example.virgo.route.appointment.SearchFacilityRoute
import com.example.virgo.route.ecommerce.CartRoute
import com.example.virgo.route.ecommerce.CheckOutRoute
import com.example.virgo.route.profile.OrderTrackingRoute
import com.example.virgo.route.ecommerce.PrescriptionRoute
import com.example.virgo.route.ecommerce.ProductDetailRoute
import com.example.virgo.route.profile.ChangeAddressRoute
import com.example.virgo.route.profile.EditPersonalRoute
import com.example.virgo.route.profile.ManageAddressRoute
import com.example.virgo.route.profile.PersonalInformationRoute
import com.example.virgo.route.profile.ProfileRoute
import com.example.virgo.route.reminder.AddFormRoute
import com.example.virgo.route.reminder.ReminderListRoute
import com.example.virgo.route.reminder.ReminderTimeRoute
import com.example.virgo.route.reminder.SearchToReminderRoute
import com.example.virgo.route.search.SearchRoute
import com.example.virgo.ui.screen.appointment.AppointmentBookingScreen
import com.example.virgo.ui.screen.appointment.FacilityDetailScreen
import com.example.virgo.ui.screen.appointment.SearchFacilityScreen
import com.example.virgo.ui.screen.article.ArticleScreen
import com.example.virgo.ui.screen.auth.LoginScreen
import com.example.virgo.ui.screen.auth.SignUpScreen
import com.example.virgo.ui.screen.ecommerce.CartScreen
import com.example.virgo.ui.screen.ecommerce.CheckOutScreen
import com.example.virgo.ui.screen.ecommerce.UploadPrescriptionScreen
import com.example.virgo.ui.screen.profile.AppointmentHistoryScreen
import com.example.virgo.ui.screen.profile.ChangeAddressScreen
import com.example.virgo.ui.screen.profile.EditPersonalScreen
import com.example.virgo.ui.screen.profile.ManageAddressScreen
import com.example.virgo.ui.screen.profile.ProfileScreen
import com.example.virgo.ui.screen.reminder.AddFormScreen
import com.example.virgo.ui.screen.reminder.ReminderListScreen
import com.example.virgo.ui.screen.reminder.ReminderTimeScreen
import com.example.virgo.ui.screen.reminder.SearchToReminderScreen
import com.example.virgo.ui.screen.telemedicine.TelemedicineScreen
import com.example.virgo.ui.screen.profile.OrderTrackingScreen
import com.example.virgo.ui.screen.profile.PersonalInforScreen
import com.example.virgo.ui.theme.VirgoTheme
import kotlin.reflect.typeOf


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        SharedPreferencesManager.init(applicationContext)
        val navItems = NavItem.entries.toList()
        val uid = SharedPreferencesManager.getString("uid")

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "alarm_channel",
                    "Alarm Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Channel for alarm notifications"
                }

                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager?.createNotificationChannel(channel)
            }
        }

        fun shouldShowNavBar (routeFull: String?): Boolean {
            if (routeFull.isNullOrEmpty()) {
                return true
            }

            val route = routeFull.split(".").last()
            for (navItem in navItems) {
                if (navItem.route.toString().contains(route)) {
                    return true
                }
            }

            return false
        }

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
                        startDestination = if (uid.isNullOrEmpty()) LoginRoute else HomeRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<SignupRoute> {
                            SignUpScreen(navController)
                        }
                        composable<LoginRoute> {
                            LoginScreen(navController)
                        }
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
                            ArticleScreen(html, navController)
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
                        composable<CartRoute> {
                            CartScreen(navController)
                        }
                        composable<ProfileRoute> {
                            ProfileScreen(navController)
                        }
                        composable<PrescriptionRoute> {
                            UploadPrescriptionScreen(navController)
                        }
                        composable<CheckOutRoute> {
                            val cartItemIdList = it.toRoute<CheckOutRoute>().cartItemIdList
                            CheckOutScreen(cartItemIdList, navController)
                        }
                        composable<OrderTrackingRoute> (
                            typeMap = mapOf(
                                typeOf<OrderStatus>() to NavType.EnumType(OrderStatus::class.java)
                            )
                        ) {
                            val status = it.toRoute<OrderTrackingRoute>().status
                            OrderTrackingScreen(status, navController)
                        }
                        composable<PersonalInformationRoute> {
                            PersonalInforScreen(navController)
                        }
                        composable<EditPersonalRoute> {
                            EditPersonalScreen(navController)
                        }
                        composable<ManageAddressRoute> {
                            ManageAddressScreen(navController)
                        }
                        composable<ChangeAddressRoute> {
                            val index = it.toRoute<ChangeAddressRoute>().index
                            ChangeAddressScreen(navController, index)
                        }
                        composable<ReminderListRoute> {
                            ReminderListScreen(navController)
                        }
                        composable<SearchToReminderRoute> {
                            SearchToReminderScreen(navController)
                        }
                        composable<AddFormRoute> {
                            val productIDs = it.toRoute<AddFormRoute>().products
                            AddFormScreen(productIDs, navController)
                        }
                        composable<ReminderTimeRoute> (
                            typeMap = hashMapOf(
                                typeOf<Reminder>() to CustomNavType.ReminderType
                            )
                        ) {
                            val reminder = it.toRoute<ReminderTimeRoute>().reminder
                            ReminderTimeScreen(reminder, navController)
                        }
                    }
                }
            }
        }

    }


}

