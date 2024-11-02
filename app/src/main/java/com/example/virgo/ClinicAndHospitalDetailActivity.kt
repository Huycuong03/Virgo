package com.example.virgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ClinicAndHospitalDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClinicAndHospitalDetail()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicAndHospitalDetail() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Trung tâm Xét nghiệm Y khoa Medilab Sài Gòn") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Handle back action */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F5FB))
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF1F5FB))
            ) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.image_holder), // Thay bằng ảnh của bạn
                        contentDescription = "Hospital Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                item { TabSection(selectedTab, onTabSelected = { selectedTab = it }) }
                item { TabContent(selectedTab) }
            }
        }
    }
}

@Composable
fun TabSection(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(8.dp)
    ) {
        TabButton("Thông tin cơ bản", selected = selectedTab == 0) { onTabSelected(0) }
        TabButton("Đánh giá", selected = selectedTab == 1) { onTabSelected(1) }
        TabButton("Câu hỏi thường gặp", selected = selectedTab == 2) { onTabSelected(2) }
    }
}

@Composable
fun TabButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onClick() }
            .background(
                color = if (selected) Color(0xFF007BFF) else Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TabContent(selectedTab: Int) {
    when (selectedTab) {
        0 -> BasicInfoContent()
        1 -> ReviewContent()
        2 -> FAQContent()
    }
}

@Composable
fun BasicInfoContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Giờ làm việc", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Thứ Hai - Chủ Nhật: 06:00 - 19:00", fontSize = 16.sp)

        ExpandableSection("Thông tin bệnh viện") {
            Text(
                "Trung tâm Xét nghiệm Y khoa Medilab được thành lập vào tháng 9/2019 với chuỗi hệ thống 7 chi nhánh trải đều khắp các tỉnh thành. Trung tâm hiện đang triển khai các gói dịch vụ kiểm tra sức khỏe toàn diện, chẩn đoán hình ảnh cũng như các hạng mục xét nghiệm từ đơn giản đến chuyên sâu.",
                fontSize = 16.sp
            )
        }

        ExpandableSection("Chuyên khoa") {
            Text("- Đa khoa\n- Chẩn đoán hình ảnh", fontSize = 16.sp)
        }

        ExpandableSection("Cơ sở vật chất") {
            Text(
                "- Phòng xét nghiệm\n- Máy xét nghiệm miễn dịch tự động\n- Máy xét nghiệm sinh hóa tự động\n- Máy xét nghiệm huyết học\n- Giường bệnh",
                fontSize = 16.sp
            )
        }

        ExpandableSection("Dịch vụ") {
            val services = listOf(
                "Gói xét nghiệm tổng quát - 2,500,000đ",
                "Gói xét nghiệm dị ứng - giun sán - 2,820,000đ",
                "Gói xét nghiệm bệnh lây qua đường tình dục - 150,000đ"
            )
            Column {
                services.forEach { service ->
                    Text(service, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        ExpandableSection("Hướng dẫn đặt lịch khám") {
            Text(
                "1. Chọn gói khám\n2. Đặt lịch hẹn\n3. Thanh toán\n4. Nhận xác nhận lịch hẹn",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Hình thức thanh toán", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            PaymentMethod(iconRes = R.drawable.ic_visa, text = "Visa")
            PaymentMethod(iconRes = R.drawable.ic_qr_code, text = "QR Code")
            PaymentMethod(iconRes = R.drawable.ic_cash, text = "Tiền mặt")
        }
    }
}

@Composable
fun ExpandableSection(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp)
        )
        if (expanded) {
            content()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PaymentMethod(iconRes: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(40.dp)
        )
        Text(text, fontSize = 14.sp)
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

@Composable
fun FAQContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Câu hỏi thường gặp", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        FAQItem(question = "Tôi có cần đặt lịch trước khi đến không?", answer = "Có, vui lòng đặt lịch trước.")
        FAQItem(question = "Trung tâm có nhận xét nghiệm vào cuối tuần không?", answer = "Có, trung tâm làm việc cả cuối tuần.")
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 8.dp)
    ) {
        Text(question, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (expanded) Icons.Default.Add else Icons.Default.Add,
            contentDescription = "Expand Answer"
        )
    }

    if (expanded) {
        Text(answer, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewClinicAndHospitalDetail() {
    ClinicAndHospitalDetail()
}
