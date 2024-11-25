package com.example.virgo.ui.screen.ecommerce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.virgo.R
import com.example.virgo.ui.theme.ColorAccent
import com.example.virgo.ui.theme.VirgoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(id: String, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { /* handle back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }

                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /* Handle add to cart */ },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_200))
                ) {
                    Text("Thêm vào giỏ", color = ColorAccent)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {/*handle*/},
                    colors = ButtonDefaults.buttonColors(containerColor = ColorAccent)
                ) {
                    Text("Chọn mua", color = Color.White)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.image_holder), // Placeholder image
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }

            item {
                Text(
                    text = "Viên Tinh Nghệ Mật Ong Sữa Chua Honeyland tăng cường đề kháng, sức khỏe (120g)",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "196.000đ",
                        style = MaterialTheme.typography.displaySmall.copy(fontSize = 24.sp),
                        fontWeight = FontWeight.Bold,
                        color = ColorAccent
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "+196 điểm thưởng",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFFFFA726)
                    )
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Đổi trả trong 30 ngày", fontSize = 12.sp)
                    Text("Miễn phí 100% đổi thuốc", fontSize = 12.sp)
                    Text("Miễn phí vận chuyển", fontSize = 12.sp)
                }
            }

            item{
                ProductReviewScreen()
            }
        }
    }
}

@Composable
fun ProductReviewScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Đánh giá sản phẩm (2 đánh giá)", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Trung bình", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "5", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = colorResource(id = R.color.teal_200),
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            RatingBarRow(stars = 5, count = 2)
            RatingBarRow(stars = 4, count = 0)
            RatingBarRow(stars = 3, count = 0)
            RatingBarRow(stars = 2, count = 0)
            RatingBarRow(stars = 1, count = 0)
        }


        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Spacer(modifier = Modifier.height(16.dp))

        UserReview(
            name = "Oanh",
            rating = 5.0f,
            comment = "sản phẩm tốt",
            date = "22 ngày trước"
        )

        Spacer(modifier = Modifier.height(8.dp))

        ReplyComment(
            name = "Lữ Thị Anh Thư",
            role = "Dược sĩ",
            comment = "Chào bạn Oanh, Dạ rất cảm ơn tình cảm của bạn dành cho nhà thuốc FPT Long châu. Bất cứ khi nào bạn cần...",
            date = "22 ngày trước"
        )
    }
}

@Composable
fun RatingBarRow(stars: Int, count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row {
            repeat(stars) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = colorResource(id = R.color.teal_200),
                    modifier = Modifier.size(16.dp)
                )
            }
            repeat(5 - stars) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        LinearProgressIndicator(
            progress = if (stars == 5) 1f else 0f,
            color = ColorAccent,
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$count", fontSize = 12.sp)
    }
}

@Composable
fun UserReview(name: String, rating: Float, comment: String, date: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = name, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "$rating", fontSize = 12.sp)
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = ColorAccent,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(text = comment, fontSize = 14.sp)
            Text(text = date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ReplyComment(name: String, role: String, comment: String, date: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color.Blue
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = role, fontSize = 12.sp, color = Color.Blue)
            }
            Text(text = comment, fontSize = 14.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}


@Composable
@Preview
fun PreviewProductDetailScreen(){
    VirgoTheme{
        ProductDetailScreen("0", NavController(LocalContext.current))
    }
}
