package com.example.virgo.ui.screen.appointment

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.R
import java.time.DayOfWeek
import java.time.LocalDate
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.virgo.model.appointment.Facility
import com.example.virgo.route.appointment.AppointmentBookingRoute
import com.example.virgo.ui.screen.lib.NavIcon
import com.example.virgo.ui.screen.lib.TopBar
import com.example.virgo.viewModel.FacilityDetailViewModel
import com.google.firebase.Timestamp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FacilityDetailScreen(id: String, navController: NavController) {
    val viewModel: FacilityDetailViewModel = viewModel()
    var facility by remember {
        mutableStateOf(Facility())
    }
    viewModel.loadByFacilityId(id) {
        facility = it
    }

    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        stickyHeader {
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

        item {
            FacilityDetail(facility) {
                facility.id?.let {
                    navController.navigate(AppointmentBookingRoute(it))
                }
            }
        }
    }
}

@Composable
fun FacilityDetail(facility: Facility, onBooking: () -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = stringResource(id = R.string.github_page) + "/drawable/" + facility.coverImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height((constraints.maxHeight * 0.45f).dp)
                .fillMaxWidth()
        )
        AsyncImage(
            model = stringResource(id = R.string.github_page) + "/drawable/" + facility.avatarImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 30.dp)
                .size(120.dp)
                .clip(CircleShape)
        )
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = facility.name.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = facility.address.toString(),
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(onClick = onBooking) {
        Text(text = "Book an appointment")
    }
}

@Composable
fun ReviewContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Đánh giá", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Chưa có đánh giá nào.", fontSize = 16.sp, color = Color.Gray)
    }
}