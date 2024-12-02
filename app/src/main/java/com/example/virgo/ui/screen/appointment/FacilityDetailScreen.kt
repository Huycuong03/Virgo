package com.example.virgo.ui.screen.appointment

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.model.appointment.Facility
import com.example.virgo.route.appointment.AppointmentBookingRoute
import com.example.virgo.ui.screen.lib.NavIcon
import com.example.virgo.ui.screen.lib.TopBar
import com.example.virgo.viewModel.appointment.FacilityDetailViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FacilityDetailScreen(id: String, navController: NavController) {
    val viewModel: FacilityDetailViewModel = viewModel()
    var facility by remember {
        mutableStateOf(Facility())
    }
    viewModel.loadByFacilityId(id) {
        facility = it
    }
    Scaffold(
        topBar = {
            TopBar(
                leadingIcon = {
                    NavIcon {
                        navController.popBackStack()
                    }
                },
                title = {},
                actions = {}
            )
        }
    ) {
        FacilityDetail(onBooking = {
            facility.id?.let {
                navController.navigate(AppointmentBookingRoute(it))
            }
        })
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun FacilityDetail(onBooking: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    settings.setSupportZoom(true)
                    settings.builtInZoomControls = true
                    settings.displayZoomControls = false
                    loadUrl("file:///android_asset/facility.html")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            update = {
                it.loadUrl("file:///android_asset/facility.html")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Nút đặt lịch
        Button(
            onClick = onBooking,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Book an appointment")
        }
    }
}
