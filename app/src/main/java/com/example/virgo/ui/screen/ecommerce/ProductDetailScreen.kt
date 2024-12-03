package com.example.virgo.ui.screen.ecommerce

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.virgo.R
import com.example.virgo.model.lib.Review
import com.example.virgo.route.ecommerce.CartRoute
import com.example.virgo.ui.theme.ColorAccent
import com.example.virgo.ui.theme.ColorRating
import com.example.virgo.ui.theme.ColorWarning
import com.example.virgo.ui.theme.VirgoTheme
import com.example.virgo.viewModel.CartViewModel
import com.example.virgo.viewModel.ProductDetailViewModel
import com.google.firebase.Timestamp
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(id: String, navController: NavController) {
    val viewModel: ProductDetailViewModel = viewModel()
    val product = viewModel.product.value
    LaunchedEffect(id) {
        viewModel.fetchProduct(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(CartRoute)
                    }) {
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
                    onClick = { viewModel.addProductToCart() },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_200))
                ) {
                    Text("Thêm vào giỏ", color = Color.White)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            product.let { product ->
                // Product Images
                item {
                    AsyncImage(
                        model = stringResource(R.string.github_page) +"/drawable/"+ (product.images.getOrNull(0)?:"image_holder.jpg"),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                    )
                }
                // Product Name
                item {
                    Text(
                        text = "Thương hiệu: ${product.brand?.name ?: "N/A"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = product.name ?: "No Name",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                        fontWeight = FontWeight.Bold,
                        lineHeight = 10.sp
                    )
                    Row() {
                        Text(
                            text = "•${product.id ?: "N/A"}",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "•${product.reviews.size ?: "N/A"} đánh giá",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )
                    }
                }

                // Price and Stock Quantity
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "${(product.getFormattedPrice())}đ/",
                            style = MaterialTheme.typography.displaySmall.copy(fontSize = 24.sp),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E88E5)
                        )
                        Text(
                            text = "${product.packaging?.type?.toString()}",
                            style = MaterialTheme.typography.displaySmall.copy(fontSize = 24.sp),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E88E5)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Stock: ${product.stockQuantity ?: 0}",
                            style = MaterialTheme.typography.labelMedium
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

                item {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.padding(8.dp))
                    Column() {
                        // Product Information Title
                        Text(
                            text = "Thông tin sản phẩm",
                            style = MaterialTheme.typography.bodyLarge.copy(),
                            fontWeight = FontWeight.Bold,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Category
                        Text(
                            text = "Danh mục:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = product.category ?: "N/A",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Dạng bào chế:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        //dosage
                        Text(
                            text = product.packaging?.dosageForm.toString() ?: "N/A",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Quy cách:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        // Packaging
                        Text(
                            text = "${product.packaging?.type.toString() ?: "N/A"} ${product.packaging?.quantity.toString() ?: "N/A"}",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Nhà sản xuất:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        // Manufacturer
                        Text(
                            text = product.manufacturer?.name.toString() ?: "N/A",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Nước sản xuất:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        //Country
                        Text(
                            text = product.manufacturer?.country.toString() ?: "N/A",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        // Ingredients
                        if (product.ingredients.isNotEmpty()) {
                            Text(
                                text = "Thành phần:",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            product.ingredients.forEach { ingredient ->
                                Text("- $ingredient", style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Mô tả:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        // Description
                        Text(
                            text = product.description ?: "Chưa có mô tả",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Số đăng ký:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        // Registration Number
                        Text(
                            text = product.registrationNumber ?: "N/A",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Cách dùng:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        // Instructions
                        Text(
                            text = product.instructions ?: "Chưa có hướng dẫn",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Bảo quản:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        // Preservation
                        Text(
                            text = product.preserveInstruction ?: "N/A",
                            style = MaterialTheme.typography.bodySmall.copy()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        // Warning
                        Text(
                            text = "Cảnh báo:${product.warning ?: "Không có cảnh báo"}",
                            style = MaterialTheme.typography.bodySmall.copy(),
                            color = Color.Red
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.padding(8.dp))
                    ProductReviewScreen(
                        product.reviews,
                    )
                }
            }
        }
    }
}

@Composable
fun ProductReviewScreen(reviews: List<Review>) {
//    operator fun Float.plusAssign(number: Number) {}
    var averageRating = 0F
    reviews.forEach{ review ->
        averageRating += (review.rating?:0F) }
    averageRating /= reviews.size.takeIf { it > 0 } ?: 1
    Column(modifier = Modifier.padding(16.dp)) {
        // Header
        Text(
            text = "Đánh giá sản phẩm (${reviews.size} đánh giá)",
            style = MaterialTheme.typography.bodyLarge.copy(),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Average Rating
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Trung bình", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "${averageRating}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = ColorRating,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Rating Bar Breakdown
        Column {
            RatingBarRow(stars = 5, count = reviews.count { it.rating == 5F })
            RatingBarRow(stars = 4, count = reviews.count { it.rating == 4F })
            RatingBarRow(stars = 3, count = reviews.count { it.rating == 3F })
            RatingBarRow(stars = 2, count = reviews.count { it.rating == 2F })
            RatingBarRow(stars = 1, count = reviews.count { it.rating == 1F })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Spacer(modifier = Modifier.height(16.dp))

        // Display User Reviews
        reviews.forEach { review ->
            UserReview(
                name = review.user?.name,
                rating = review.rating,
                comment = review.comment,
                date = Timestamp(review.timestamp?:0,0).toDate().toString()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
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
                    tint = ColorRating,
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
            progress = if (stars == 5) 1f else 1f,
            color = ColorRating,
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$count", fontSize = 12.sp)
    }
}

@Composable
fun UserReview(name: String?, rating: Float?, comment: String?, date: String?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            if (name != null) {
                Text(text = name, fontWeight = FontWeight.Bold)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "$rating", fontSize = 12.sp)
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = ColorAccent,
                    modifier = Modifier.size(12.dp)
                )
            }
            if (comment != null) {
                Text(text = comment, fontSize = 14.sp)
            }
            if (date != null) {
                Text(text = date, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}
